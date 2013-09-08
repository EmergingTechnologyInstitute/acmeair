package com.acmeair.astyanax.entities;

import com.acmeair.astyanax.service.AstyanaxContextHelper;
import com.netflix.astyanax.entitystore.DefaultEntityManager;
import com.netflix.astyanax.entitystore.EntityManager;

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
public class EntityManagers {

	public static final String CF_AIRPORT_CODE_MAPING = "airport_code_mapping";
	public static final String CF_FLIGHT_SEGMENT = "flight_segment";
	public static final String CF_FLIGHT = "flight";
	public static final String CF_CUSTOMER = "customer";
	public static final String CF_CUSTOMER_SESSION = "customer_session";
	public static final String CF_BOOKING = "booking";

	public static AstyanaxContextHelper helper = new AstyanaxContextHelper();
	
	public static EntityManager<BookingImpl, String> getBookingEntityManager() {
		EntityManager<BookingImpl, String> entityPersister = new DefaultEntityManager.Builder<BookingImpl, String>()
				.withEntityType(BookingImpl.class)
				.withKeyspace(helper.getKeyspace())
				.withColumnFamily(CF_BOOKING).build();
		return entityPersister;
	}

	public static EntityManager<CustomerImpl, String> getCustomerEntityManager() {
		EntityManager<CustomerImpl, String> entityPersister = new DefaultEntityManager.Builder<CustomerImpl, String>()
				.withEntityType(CustomerImpl.class)
				.withKeyspace(helper.getKeyspace())
				.withColumnFamily(CF_CUSTOMER).build();
		return entityPersister;
	}

	public static EntityManager<CustomerSessionImpl, String> getCustomerSessionEntityManager() {
		EntityManager<CustomerSessionImpl, String> entityPersister = new DefaultEntityManager.Builder<CustomerSessionImpl, String>()
				.withEntityType(CustomerSessionImpl.class)
				.withKeyspace(helper.getKeyspace())
				.withColumnFamily(CF_CUSTOMER).build();
		return entityPersister;
	}

	public static EntityManager<AirportCodeMappingImpl, String> getAiportMappingEntityManager() {
		EntityManager<AirportCodeMappingImpl, String> entityPersister = new DefaultEntityManager.Builder<AirportCodeMappingImpl, String>()
				.withEntityType(AirportCodeMappingImpl.class)
				.withKeyspace(helper.getKeyspace())
				.withColumnFamily(CF_AIRPORT_CODE_MAPING).build();

		return entityPersister;
	}

	public static EntityManager<FlightSegmentImpl, String> getFlightSegmentEntityManager() {
		EntityManager<FlightSegmentImpl, String> entityPersister = new DefaultEntityManager.Builder<FlightSegmentImpl, String>()
				.withEntityType(FlightSegmentImpl.class)
				.withKeyspace(helper.getKeyspace())
				.withColumnFamily(CF_FLIGHT_SEGMENT).build();
		return entityPersister;
	}

	public static EntityManager<FlightImpl, String> getFlightEntityManager() {
		EntityManager<FlightImpl, String> entityPersister = new DefaultEntityManager.Builder<FlightImpl, String>()
				.withEntityType(FlightImpl.class)
				.withKeyspace(helper.getKeyspace()).withColumnFamily(CF_FLIGHT)
				.build();
		return entityPersister;
	}
}
