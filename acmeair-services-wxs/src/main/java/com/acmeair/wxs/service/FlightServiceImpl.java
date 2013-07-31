/*******************************************************************************
 * Copyright (c) 2013 IBM Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.acmeair.wxs.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acmeair.entities.AirportCodeMapping;
import com.acmeair.entities.Flight;
import com.acmeair.entities.FlightSegment;
import com.acmeair.service.FlightService;
import com.acmeair.service.FlightServiceLoader;
import com.acmeair.service.KeyGenerator;
import com.acmeair.wxs.entities.AirportCodeMappingImpl;
import com.acmeair.wxs.entities.FlightImpl;
import com.acmeair.wxs.entities.FlightPartitionableKey;
import com.acmeair.wxs.entities.FlightSegmentImpl;
import com.acmeair.wxs.utils.MapPutAllAgent;
import com.acmeair.wxs.utils.WXSSessionManager;
import com.ibm.websphere.objectgrid.ObjectMap;
import com.ibm.websphere.objectgrid.Session;
import com.ibm.websphere.objectgrid.datagrid.AgentManager;
import com.ibm.websphere.objectgrid.query.ObjectQuery;

@Service("wxsFlightService")
public class FlightServiceImpl implements FlightService, FlightServiceLoader {

	private static final String FLIGHT_MAP_NAME = "Flight";
	private static final String FLIGHT_SEGMENT_MAP_NAME = "FlightSegment";
	private static final String AIRPORT_CODE_MAPPING_MAP_NAME = "AirportCodeMapping";

	@Autowired
	private WXSSessionManager sessionManager;

	@Resource
	KeyGenerator keyGenerator;

	// TODO: consider adding time based invalidation to these maps
	private static ConcurrentHashMap<String, FlightSegment> originAndDestPortToSegmentCache = new ConcurrentHashMap<String, FlightSegment>();
	private static ConcurrentHashMap<String, List<FlightImpl>> flightSegmentAndDataToFlightCache = new ConcurrentHashMap<String, List<FlightImpl>>();
	private static ConcurrentHashMap<FlightPartitionableKey, FlightImpl> flightPKtoFlightCache = new ConcurrentHashMap<FlightPartitionableKey, FlightImpl>();
	
	protected FlightImpl getFlightByFlightKey(FlightPartitionableKey key) {
		try {
			FlightImpl flight;
			flight = flightPKtoFlightCache.get(key);
			if (flight == null) {
				Session session = sessionManager.getObjectGridSession();
				ObjectMap flightMap = session.getMap(FLIGHT_MAP_NAME);
				flight = (FlightImpl) flightMap.get(key);
				if (key != null && flight != null) {
					flightPKtoFlightCache.putIfAbsent(key, flight);
				}
			}
			return flight;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<? extends Flight> getFlightByAirportsAndDepartureDate(
			String fromAirport, String toAirport, Date deptDate) {
		try {
			Session session = null;
			String originPortAndDestPortQueryString = fromAirport + toAirport;
			FlightSegment segment = originAndDestPortToSegmentCache
					.get(originPortAndDestPortQueryString);
			boolean startedTran = false;

			if (segment == null) {
				session = sessionManager.getObjectGridSession();
				if (!session.isTransactionActive()) {
					startedTran = true;
					session.begin();
				}
				ObjectQuery query = session
						.createObjectQuery("select obj from FlightSegment obj where obj.destPort=?1 and obj.originPort=?2");
				query.setParameter(2, fromAirport);
				query.setParameter(1, toAirport);

				int partitionId = sessionManager
						.getBackingMap(FLIGHT_SEGMENT_MAP_NAME)
						.getPartitionManager().getPartition(fromAirport);
				query.setPartition(partitionId);

				@SuppressWarnings("unchecked")
				Iterator<Object> itr = query.getResultIterator();
				if (itr.hasNext())
					segment = (FlightSegment) itr.next();

				if (segment == null) {
					segment = new FlightSegmentImpl(); // put a sentinel value
														// of a non-populated
														// flightsegment
				}
				originAndDestPortToSegmentCache.putIfAbsent(
						originPortAndDestPortQueryString, segment);
			}

			// cache flights that not available (checks against sentinel value
			// above indirectly)
			if (segment.getId() == null) {
				return new ArrayList<FlightImpl>();
			}

			String segId = segment.getId();
			String flightSegmentIdAndScheduledDepartureTimeQueryString = segId
					+ deptDate.toString();
			List<FlightImpl> flights = flightSegmentAndDataToFlightCache
					.get(flightSegmentIdAndScheduledDepartureTimeQueryString);

			if (flights == null) {
				flights = new ArrayList<FlightImpl>();
				if (session == null) {
					session = sessionManager.getObjectGridSession();
					if (!session.isTransactionActive()) {
						startedTran = true;
						session.begin();
					}
				}
				FlightImpl flight;
				// Has to make the partition field last otherwise getting
				// exception
				ObjectQuery query = session
						.createObjectQuery("select obj from Flight obj where obj.scheduledDepartureTime=?1 and obj.flightSegmentId=?2");
				query.setParameter(1, deptDate);
				query.setParameter(2, segId);

				int partitionId = sessionManager.getBackingMap(FLIGHT_MAP_NAME)
						.getPartitionManager().getPartition(segId);
				query.setPartition(partitionId);
				@SuppressWarnings("unchecked")
				Iterator<Object> itr = query.getResultIterator();

				while (itr.hasNext()) {
					flight = (FlightImpl) itr.next();
					flight.setFlightSegment(segment);
					flights.add(flight);
				}

				flightSegmentAndDataToFlightCache.putIfAbsent(
						flightSegmentIdAndScheduledDepartureTimeQueryString,
						flights);

				if (startedTran)
					session.commit();
			}
			return flights;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<FlightImpl> getFlightByAirports(String fromAirport,
			String toAirport) {

		try {

			Session session = sessionManager.getObjectGridSession();

			boolean startedTran = false;
			if (!session.isTransactionActive()) {
				startedTran = true;
				session.begin();
			}
			ObjectQuery query = session
					.createObjectQuery("select obj from FlightSegment obj where obj.destPort=?1 and obj.originPort=?2");
			query.setParameter(2, fromAirport);
			query.setParameter(1, toAirport);

			int partitionId = sessionManager
					.getBackingMap(FLIGHT_SEGMENT_MAP_NAME)
					.getPartitionManager().getPartition(fromAirport);
			query.setPartition(partitionId);

			@SuppressWarnings("unchecked")
			Iterator<Object> itr = query.getResultIterator();
			FlightSegment segment = null;
			if (itr.hasNext())
				segment = (FlightSegment) itr.next();

			FlightImpl flight;
			List<FlightImpl> flights = new ArrayList<FlightImpl>();
			if (segment != null) {
				String segId = segment.getId();
				// Has to make the partition field last otherwise getting
				// exception
				query = session
						.createObjectQuery("select obj from Flight obj where obj.flightSegmentId=?1");
				query.setParameter(1, segId);

				partitionId = sessionManager.getBackingMap(FLIGHT_MAP_NAME)
						.getPartitionManager().getPartition(segId);
				query.setPartition(partitionId);

				@SuppressWarnings("unchecked")
				Iterator<Object> itr2 = query.getResultIterator();
				while (itr2.hasNext()) {
					flight = (FlightImpl) itr2.next();
					flight.setFlightSegment(segment);
					flights.add(flight);
				}
			}
			if (startedTran)
				session.commit();

			return flights;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void storeAirportMapping(AirportCodeMapping mapping) {
		try {
			Session session = sessionManager.getObjectGridSession();
			ObjectMap airportCodeMappingMap = session
					.getMap(AIRPORT_CODE_MAPPING_MAP_NAME);
			airportCodeMappingMap.insert(mapping.getAirportCode(), mapping);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public FlightImpl createNewFlight(String flightSegmentId,
			Date scheduledDepartureTime, Date scheduledArrivalTime,
			BigDecimal firstClassBaseCost, BigDecimal economyClassBaseCost,
			int numFirstClassSeats, int numEconomyClassSeats,
			String airplaneTypeId) {
		try {
			String id = keyGenerator.generate().toString();
			FlightImpl flight = new FlightImpl(id, flightSegmentId,
					scheduledDepartureTime, scheduledArrivalTime,
					firstClassBaseCost, economyClassBaseCost,
					numFirstClassSeats, numEconomyClassSeats, airplaneTypeId);
			Session session = sessionManager.getObjectGridSession();
			ObjectMap flightMap = session.getMap(FLIGHT_MAP_NAME);
			flightMap.insert(flight.getKey(), flight);
			return flight;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void storeFlightSegment(FlightSegment flightSeg) {
		try {
			Session session = sessionManager.getObjectGridSession();
			ObjectMap flightSegmentMap = session
					.getMap(FLIGHT_SEGMENT_MAP_NAME);
			// As the partition is on a field, the insert will not work so we
			// use agent instead
			// flightSegmentMap.insert(flightSeg.getFlightName(), flightSeg);
			AgentManager agMgr = flightSegmentMap.getAgentManager();
			MapPutAllAgent putAllAgent = new MapPutAllAgent(); // Not using the
																// true object
																// type so that
																// serializer
																// can have
																// better
																// performance
			HashMap<Object, HashMap<Object, Object>> objectsToSave = new HashMap<Object, HashMap<Object, Object>>();
			Object partitionKey = flightSeg.getOriginPort();

			HashMap<Object, Object> keysAndObjectsToSave = new HashMap<Object, Object>();
			keysAndObjectsToSave.put(flightSeg.getId(), flightSeg);

			objectsToSave.put(partitionKey, keysAndObjectsToSave);

			putAllAgent.setObjectsToSave(objectsToSave);
			agMgr.callMapAgent(putAllAgent, objectsToSave.keySet());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public AirportCodeMapping newAirportMapping(String airportCode,
			String airportName) {
		// TODO Auto-generated method stub
		return new AirportCodeMappingImpl(airportCode, airportName);
	}


	@Override
	public FlightSegment newFlightSegment(String id, String origPort,
			String destPort, int miles) {
		// TODO Auto-generated method stub
		return new FlightSegmentImpl(id, origPort, destPort, miles);
	}

}
