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

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acmeair.entities.Booking;
import com.acmeair.entities.Customer;
import com.acmeair.service.BookingService;
import com.acmeair.service.CustomerService;
import com.acmeair.service.KeyGenerator;
import com.acmeair.wxs.entities.BookingImpl;
import com.acmeair.wxs.entities.BookingPartitionableKey;
import com.acmeair.wxs.entities.FlightImpl;
import com.acmeair.wxs.entities.FlightPartitionableKey;
import com.acmeair.wxs.utils.WXSSessionManager;
import com.ibm.websphere.objectgrid.ObjectMap;
import com.ibm.websphere.objectgrid.Session;
import com.ibm.websphere.objectgrid.query.ObjectQuery;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

@Service("bookingService")
public class BookingServiceImpl implements BookingService {

	private static final String BOOKING_MAP_NAME = "Booking";

	@Autowired
	private WXSSessionManager sessionManager;

	@Resource
	FlightServiceImpl flightService;

	@Resource
	CustomerService customerService;

	@Resource
	KeyGenerator keyGenerator;

	@Override
	public BookingImpl bookFlight(String customerId, String flightId,
			String segmentId) {
		try {
			// We still delegate to the flight and customer service for the map
			// access than getting the map instance directly
			FlightImpl f = flightService
					.getFlightByFlightKey(new FlightPartitionableKey(segmentId,
							flightId));
			Customer c = customerService.getCustomerByUsername(customerId);

			BookingImpl newBooking = new BookingImpl(keyGenerator.generate()
					.toString(), new Date(), c, f);
			BookingPartitionableKey key = newBooking.getBookingKey();

			Session session = sessionManager.getObjectGridSession();
			ObjectMap bookingMap = session.getMap(BOOKING_MAP_NAME);
			bookingMap.put(key, newBooking);
			return newBooking;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Booking getBooking(String user, String id) {

		try {
			Session session = sessionManager.getObjectGridSession();
			ObjectMap bookingMap = session.getMap(BOOKING_MAP_NAME);

			return (Booking) bookingMap.get(new BookingPartitionableKey(user,
					id));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void cancelBooking(String user, String id) {
		try {
			Session session = sessionManager.getObjectGridSession();
			ObjectMap bookingMap = session.getMap(BOOKING_MAP_NAME);

			bookingMap.remove(new BookingPartitionableKey(user, id));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<BookingImpl> getBookingsByUser(String user) {
		try {
			Session session = sessionManager.getObjectGridSession();

			boolean startedTran = false;
			if (!session.isTransactionActive()) {
				startedTran = true;
				session.begin();
			}
			ObjectQuery query = session
					.createObjectQuery("select obj from Booking obj where obj.customerId=?1");
			query.setParameter(1, user);

			int partitionId = sessionManager.getBackingMap(BOOKING_MAP_NAME)
					.getPartitionManager().getPartition(user);
			query.setPartition(partitionId);

			List<BookingImpl> list = new ArrayList<BookingImpl>();
			@SuppressWarnings("unchecked")
			Iterator<Object> itr = query.getResultIterator();
			while (itr.hasNext())
				list.add((BookingImpl) itr.next());
			if (startedTran)
				session.commit();

			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
