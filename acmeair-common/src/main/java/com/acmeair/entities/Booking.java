package com.acmeair.entities;

import java.util.Date;

public interface Booking {
	
	public abstract String getBookingId();

	public abstract void setFlight(Flight flight);

	public abstract Date getDateOfBooking();

	public abstract void setDateOfBooking(Date dateOfBooking);

	public abstract Customer getCustomer();

	public abstract Flight getFlight();

}