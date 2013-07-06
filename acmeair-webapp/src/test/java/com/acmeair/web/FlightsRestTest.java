package com.acmeair.web;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.acmeair.entities.Flight;

public class FlightsRestTest {
	
	static FlightsREST flightsRest;
	
	@BeforeClass
	static public void setup()
	{
		flightsRest= new FlightsREST();
	}

	@Test
	public void testGetTripFlights() {
		TripFlightOptions twoWay = flightsRest.browseFlights("CAI", "SIN", true);
		List<TripLegInfo> tripLegList = twoWay.getTripFlights();
		for (TripLegInfo tripLegInfo : tripLegList) {
			List<? extends Flight>flights = tripLegInfo.getFlightsOptions();
			for (Flight flight : flights) {
				TripFlightOptions tfo = flightsRest.getTripFlights("CAI", "SIN", flight.getScheduledDepartureDate(), null, true);
				assertTrue(tfo.getTripFlights().size()>0);
			}
		}
	}

	@Test
	public void testBrowseFlights() {
		TripFlightOptions oneWay = flightsRest.browseFlights("IKA", "BOM", true);
		TripFlightOptions twoWay = flightsRest.browseFlights("CAI", "SIN", false);
		
		assertTrue(twoWay.getTripLegs()==2);
		assertTrue(oneWay.getTripLegs()==1);
	}

}
