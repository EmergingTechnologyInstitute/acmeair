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
package com.acmeair.astyanax.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.acmeair.astyanax.entities.AirportCodeMappingImpl;
import com.acmeair.astyanax.entities.EntityManagers;
import com.acmeair.astyanax.entities.FlightImpl;
import com.acmeair.astyanax.entities.FlightSegmentImpl;
import com.acmeair.entities.AirportCodeMapping;
import com.acmeair.entities.FlightSegment;
import com.acmeair.service.FlightService;
import com.acmeair.service.FlightServiceLoader;
import com.acmeair.service.KeyGenerator;
import com.netflix.astyanax.entitystore.EntityManager;

@Service("astyanaxFlightService")
public class FlightServiceImpl implements FlightService, FlightServiceLoader {

	@Resource
	KeyGenerator keyGenerator = new DefaultKeyGeneratorImpl();

	// TODO: consider adding time based invalidation to these maps
	private static ConcurrentHashMap<String, FlightSegment> originAndDestPortToSegmentCache = new ConcurrentHashMap<String, FlightSegment>();
	private static ConcurrentHashMap<String, List<FlightImpl>> flightSegmentAndDataToFlightCache = new ConcurrentHashMap<String, List<FlightImpl>>();
	private static ConcurrentHashMap<String, FlightImpl> flightPKtoFlightCache = new ConcurrentHashMap<String, FlightImpl>();

	protected FlightImpl getFlightByFlightKey(String keyObj) {

		FlightImpl flight;
		String key = (String) keyObj;
		flight = flightPKtoFlightCache.get(key);
		if (flight == null) {

			flight = EntityManagers.getFlightEntityManager().get(key);

			if (key != null && flight != null) {
				flightPKtoFlightCache.putIfAbsent(key, flight);
			}
		}
		return flight;

	}

	@Override
	public List<FlightImpl> getFlightByAirportsAndDepartureDate(String fromAirport,
			String toAirport, Date deptDate) {
		EntityManager<FlightSegmentImpl, String> entityManager = EntityManagers
				.getFlightSegmentEntityManager();

		EntityManager<FlightImpl, String> flighEntityManager = EntityManagers
				.getFlightEntityManager();

		List<FlightSegmentImpl> flightSegments = entityManager.find(String
				.format("SELECT * FROM %s WHERE %s=%s AND %s=%s;",
						EntityManagers.CF_FLIGHT_SEGMENT, "originPort",
						fromAirport, "destPort", toAirport));

		List<FlightImpl> result = new ArrayList<FlightImpl>();

		for (FlightSegment flightSegment : flightSegments) {
			String query = String.format(
					"SELECT * FROM %s WHERE %s=%s AND %s=%s;", EntityManagers.CF_FLIGHT,
					"flightSegmentId", flightSegment.getId(),"scheduledDepartureDate",deptDate.getTime());
			
			List<FlightImpl> flights = flighEntityManager.find(query);
			
			for (FlightImpl flightImpl : flights) {
				flightImpl.setFlightSegment(flightSegment);
				result.add(flightImpl);
			}
		}

		return result;
		
	}

	@Override
	public List<FlightImpl> getFlightByAirports(String fromAirport,
			String toAirport) {

		EntityManager<FlightSegmentImpl, String> entityManager = EntityManagers
				.getFlightSegmentEntityManager();

		EntityManager<FlightImpl, String> flighEntityManager = EntityManagers
				.getFlightEntityManager();

		String cql = String
				.format("SELECT * FROM %s WHERE %s=%s AND %s=%s;",
						EntityManagers.CF_FLIGHT_SEGMENT, "originPort",
						fromAirport, "destPort", toAirport);
		System.out.println(cql);
		List<FlightSegmentImpl> flightSegments = entityManager.find(cql);

		List<FlightImpl> result = new ArrayList<FlightImpl>();

		for (FlightSegment flightSegment : flightSegments) {

			cql = String.format(
					"SELECT * FROM %s WHERE %s=%s;", EntityManagers.CF_FLIGHT,
					"flightSegmentId", flightSegment.getId());
			System.out.println(cql);
			List<FlightImpl> flights = flighEntityManager.find(cql);
			for (FlightImpl flightImpl : flights) {
				flightImpl.setFlightSegment(flightSegment);
				result.add(flightImpl);
			}
		}

		return result;

	}

	@Override
	public void storeAirportMapping(AirportCodeMapping mapping) {

		EntityManager<AirportCodeMappingImpl, String> entityManager = EntityManagers
				.getAiportMappingEntityManager();

		entityManager.put((AirportCodeMappingImpl) mapping);
	}


	@Override
	public FlightImpl createNewFlight(String flightSegmentId,
			Date scheduledDepartureTime, Date scheduledArrivalTime,
			BigDecimal firstClassBaseCost, BigDecimal economyClassBaseCost,
			int numFirstClassSeats, int numEconomyClassSeats,
			String airplaneTypeId) {

		String id = keyGenerator.generate().toString();
		FlightImpl flight = new FlightImpl(id, flightSegmentId,
				scheduledDepartureTime, scheduledArrivalTime,
				firstClassBaseCost, economyClassBaseCost, numFirstClassSeats,
				numEconomyClassSeats, airplaneTypeId);

		EntityManagers.getFlightEntityManager().put(flight);

		return flight;
	}


	@Override
	public void storeFlightSegment(FlightSegment flightSeg) {

		EntityManager<FlightSegmentImpl, String> entityManager = EntityManagers
				.getFlightSegmentEntityManager();
		entityManager.put((FlightSegmentImpl) flightSeg);

	}
	
	@Override
	public AirportCodeMapping newAirportMapping(String airportCode,
			String airportName) {
		return new AirportCodeMappingImpl(airportCode, airportName);
	}

	@Override
	public FlightSegment newFlightSegment(String id, String origPort,
			String destPort, int miles) {

		return new FlightSegmentImpl(id, origPort, destPort, miles);
	}

}
