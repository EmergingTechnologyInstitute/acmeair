package com.acmeair.astyanax.service.test;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import com.acmeair.astyanax.entities.AirportCodeMappingImpl;
import com.acmeair.astyanax.entities.FlightImpl;
import com.acmeair.astyanax.entities.FlightSegmentImpl;
import com.acmeair.astyanax.service.FlightServiceImpl;
import com.acmeair.entities.AirportCodeMapping;
import com.acmeair.entities.FlightSegment;

public class FlightServiceTest {

	@SuppressWarnings("deprecation")
	@Test
	public void testGetFlightByAirportsAndDepartureDate() {
		FlightServiceImpl service = new FlightServiceImpl();
		System.out.println(service.getFlightByAirportsAndDepartureDate("AKL", "JFK",new Date(2013-1900,6,17)));
	}

	@Test
	public void testGetFlightByAirports() {
		FlightServiceImpl service = new FlightServiceImpl();
		System.out.println(service.getFlightByAirports("AKL", "JFK"));
	}

	@Test
	public void testStoreAirportMapping() {

		AirportCodeMapping acm1 = new AirportCodeMappingImpl("LAX",
				"Los Angeles");
		AirportCodeMapping acm2 = new AirportCodeMappingImpl("JFK", "New York");

		FlightServiceImpl service = new FlightServiceImpl();
		service.storeAirportMapping(acm1);
		service.storeAirportMapping(acm2);

	}

	@SuppressWarnings("deprecation")
	@Test
	public void testCreateNewFlight() {
		FlightServiceImpl service = new FlightServiceImpl();
		FlightImpl flight = service.createNewFlight("AA1", new Date(2013 - 1900, 11, 13, 10, 30),
				new Date(2013 - 1900, 11, 13, 8, 30), new BigDecimal(8356),
				new BigDecimal(450), 12, 346, "77W");
		System.out.println(flight);
	}

	@Test
	public void testStoreFlightSegment() {
		FlightSegment fs = new FlightSegmentImpl("AA1", "JFK", "LAX", 3000);
		FlightServiceImpl service = new FlightServiceImpl();
		service.storeFlightSegment(fs);		
	}

}
