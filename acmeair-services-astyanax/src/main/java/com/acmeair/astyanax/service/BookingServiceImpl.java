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

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.acmeair.astyanax.entities.BookingImpl;
import com.acmeair.astyanax.entities.EntityManagers;
import com.acmeair.astyanax.entities.FlightImpl;
import com.acmeair.entities.Customer;
import com.acmeair.service.BookingService;
import com.acmeair.service.KeyGenerator;
import com.netflix.astyanax.entitystore.EntityManager;


@Service("astyanaxBookingService")
public class BookingServiceImpl implements BookingService {
	
	FlightServiceImpl flightService = new FlightServiceImpl();
	
	CustomerServiceImpl customerService = new CustomerServiceImpl();
	
	@Resource
	KeyGenerator keyGenerator = new DefaultKeyGeneratorImpl();
	
	@Override
	public BookingImpl bookFlight(String customerId, String flightId, String segmentId) {
		try{
			// We still delegate to the flight and customer service for the map access than getting the map instance directly
			FlightImpl f = flightService.getFlightByFlightKey(flightId);
			Customer c = customerService.getCustomerByUsername(customerId);
			
			BookingImpl newBooking = new BookingImpl(keyGenerator.generate().toString(), new Date(), c, f);			
			
			EntityManager<BookingImpl, String> entityManager = EntityManagers.getBookingEntityManager();
			entityManager.put(newBooking);
			return newBooking;
		}catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public BookingImpl getBooking(String user, String id) {
		
		try{
			EntityManager<BookingImpl, String> entityManager = EntityManagers.getBookingEntityManager();
			return entityManager.get(id);
		}catch (Exception e)
		{
			throw new RuntimeException(e);
		}
			
	}

	@Override
	public void cancelBooking(String user, String id) {
		try{
			EntityManager<BookingImpl, String> entityManager = EntityManagers.getBookingEntityManager();			
			entityManager.delete(id);
		}catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}	
	
	@Override
	public List<BookingImpl> getBookingsByUser(String user) {
		try{			
			EntityManager<BookingImpl, String> entityManager = EntityManagers.getBookingEntityManager();
			String sql = String.format(
					"SELECT * FROM %s WHERE %s='%s';", EntityManagers.CF_BOOKING,
					"userid", user);
			System.err.println(sql);
			List<BookingImpl> flights = entityManager.find(sql);					
			return flights;
		}catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		
	}
}
