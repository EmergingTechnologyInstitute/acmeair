package com.acmeair.astyanax.service.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.acmeair.astyanax.entities.BookingImpl;
import com.acmeair.astyanax.entities.FlightImpl;
import com.acmeair.astyanax.service.BookingServiceImpl;
import com.acmeair.astyanax.service.FlightServiceImpl;

public class BookingServiceTest {

	@Test
	public void testBookFlight() {
		BookingServiceImpl bookingService = new BookingServiceImpl();
		FlightServiceImpl service = new FlightServiceImpl();
		List<FlightImpl> flights = service.getFlightByAirports("AKL", "JFK");
		for (FlightImpl flightImpl : flights) {
			System.out.println(bookingService.bookFlight("uid13@email.com",
					flightImpl.geFlightId(), flightImpl.getFlightSegmentId()));
		}
	}

	@Test
	public void testGetBooking() {
		BookingServiceImpl bookingService = new BookingServiceImpl();
		List<BookingImpl> bookings = bookingService.getBookingsByUser("uid13@email.com");
		for (BookingImpl bookingImpl : bookings) {
			System.out.println(bookingService.getBooking("uid13@email.com", bookingImpl.getBookingId()));
		}

	}

	@Test
	public void testCancelBooking() {
		BookingServiceImpl bookingService = new BookingServiceImpl();
		List<BookingImpl> bookings = bookingService.getBookingsByUser("uid13@email.com");
		int i=0;
		for (BookingImpl bookingImpl : bookings) {
			bookingService.cancelBooking("uid13@email.com", bookingImpl.getBookingId());
			i++;
		}
		System.out.println(i+" cancelled bookings");
	}

	@Test
	public void testGetBookingsByUser() {
		BookingServiceImpl bookingService = new BookingServiceImpl();
		System.out.println(bookingService.getBookingsByUser("uid13@email.com").size());
	}

}
