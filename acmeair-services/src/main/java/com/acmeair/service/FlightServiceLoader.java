package com.acmeair.service;

import java.math.BigDecimal;
import java.util.Date;

import com.acmeair.entities.AirportCodeMapping;
import com.acmeair.entities.Flight;
import com.acmeair.entities.FlightSegment;

public interface FlightServiceLoader {

	public abstract void storeAirportMapping(AirportCodeMapping mapping);

	public abstract AirportCodeMapping newAirportMapping(String airportCode,
			String airportName);

	public abstract FlightSegment newFlightSegment(String id, String origPort,
			String destPort, int miles);

	public abstract void storeFlightSegment(FlightSegment flightSeg);

	public abstract Flight createNewFlight(String flightSegmentId,
			Date scheduledDepartureTime, Date scheduledArrivalTime,
			BigDecimal firstClassBaseCost, BigDecimal economyClassBaseCost,
			int numFirstClassSeats, int numEconomyClassSeats,
			String airplaneTypeId);

}
