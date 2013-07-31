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
package com.acmeair.wxs.entities;

import java.io.Serializable;
import java.util.Date;

import com.acmeair.entities.Booking;
import com.acmeair.entities.Customer;
import com.acmeair.entities.Flight;

public class BookingImpl implements Serializable, Booking {

	private static final long serialVersionUID = 1L;
	private BookingPartitionableKey pkey;

	private BookingPartitionableKey _id;
	private FlightPartitionableKey flightKey;
	private Date dateOfBooking;
	private Customer customer;
	private FlightImpl flight;

	public BookingImpl() {
	}

	public BookingImpl(String id, Date dateOfFlight, Customer customer,
			FlightImpl flight) {
		this.pkey = new BookingPartitionableKey(customer.getUsername(), id);
		this._id = this.pkey;
		this.flightKey = flight.getKey();
		this.dateOfBooking = dateOfFlight;
		this.customer = customer;
		this.flight = flight;
	}

	public BookingPartitionableKey getBookingKey() {
		return pkey;
	}

	@Override
	public String getBookingId() {
		return _id.getId();
	}

	protected String getCustomerId() {
		return pkey.getCustomerId();
	}

	protected void setKey(BookingPartitionableKey pkey) {
		this.pkey = pkey;
	}

	protected FlightPartitionableKey getFlightKey() {
		return flightKey;
	}

	protected void setFlightKey(FlightPartitionableKey flightKey) {
		this.flightKey = flightKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.acmeair.entities.BookingInterface#setFlight(com.acmeair.entities.
	 * Flight)
	 */
	@Override
	public void setFlight(Flight flight) {
		this.flight = (FlightImpl) flight;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acmeair.entities.BookingInterface#getDateOfBooking()
	 */
	@Override
	public Date getDateOfBooking() {
		return dateOfBooking;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.acmeair.entities.BookingInterface#setDateOfBooking(java.util.Date)
	 */
	@Override
	public void setDateOfBooking(Date dateOfBooking) {
		this.dateOfBooking = dateOfBooking;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acmeair.entities.BookingInterface#getCustomer()
	 */
	@Override
	public Customer getCustomer() {
		return customer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acmeair.entities.BookingInterface#getFlight()
	 */
	@Override
	public Flight getFlight() {
		return flight;
	}

	@Override
	public String toString() {
		return "Booking [key=" + pkey + ", flightKey=" + flightKey
				+ ", dateOfBooking=" + dateOfBooking + ", customer=" + customer
				+ ", flight=" + flight + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookingImpl other = (BookingImpl) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (dateOfBooking == null) {
			if (other.dateOfBooking != null)
				return false;
		} else if (!dateOfBooking.equals(other.dateOfBooking))
			return false;
		if (flight == null) {
			if (other.flight != null)
				return false;
		} else if (!flight.equals(other.flight))
			return false;
		if (flightKey == null) {
			if (other.flightKey != null)
				return false;
		} else if (!flightKey.equals(other.flightKey))
			return false;
		if (pkey == null) {
			if (other.pkey != null)
				return false;
		} else if (!pkey.equals(other.pkey))
			return false;
		return true;
	}

}
