package com.acmeair.entities;

import java.util.Date;

public interface Booking {

	//public abstract K getBookingKey();
	
	public abstract String getBookingId();

	// adding the method for index calculation
	//public abstract String getCustomerId();

	//public abstract void setKey(K pkey);

	///public abstract F getFlightKey();

	//public abstract void setFlightKey(F flightKey);

	public abstract void setFlight(Flight flight);

	public abstract Date getDateOfBooking();

	public abstract void setDateOfBooking(Date dateOfBooking);

	public abstract Customer getCustomer();

	public abstract Flight getFlight();

}