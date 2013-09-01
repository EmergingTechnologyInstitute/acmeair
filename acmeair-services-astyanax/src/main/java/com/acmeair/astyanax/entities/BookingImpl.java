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
package com.acmeair.astyanax.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.acmeair.astyanax.service.CustomerServiceImpl;
import com.acmeair.entities.Booking;
import com.acmeair.entities.Customer;
import com.acmeair.entities.Flight;

@Entity
public class BookingImpl implements Booking {

	@Id()
	private String id;

	@Column
	private String flightKey;

	@Column
	private Date dateOfBooking;

	@Column
	private FlightImpl flight;

	@Column(name = "userid")
	private String customerId;
	
	private transient Customer customer;

	public BookingImpl() {
	}

	public BookingImpl(String id, Date dateOfFlight, Customer customer,
			FlightImpl flight) {

		this.id = id;
		this.flightKey = flight.getFlightId();
		this.dateOfBooking = dateOfFlight;		
		this.flight = flight;
		this.customerId = customer.getUsername();		
	}

	@Override
	public String toString() {
		return "BookingImpl [id=" + id + ", flightKey=" + flightKey
				+ ", dateOfBooking=" + dateOfBooking + ", flight=" + flight + ", customerId=" + customerId + "]";
	}

	@Override
	public String getBookingId() {
		return id;
	}

	protected String getCustomerId() {
		return customerId;
	}

	protected String getFlightKey() {
		return flightKey;
	}

	protected void setFlightKey(String flightKey) {
		this.flightKey = flightKey;
	}

	@Override
	public void setFlight(Flight flight) {
		this.flight = (FlightImpl) flight;
	}

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
		if (customer == null) {
			CustomerServiceImpl csi = new CustomerServiceImpl();
			customer = csi.getCustomerByUsername(customerId);
		}
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result
				+ ((dateOfBooking == null) ? 0 : dateOfBooking.hashCode());
		result = prime * result + ((flight == null) ? 0 : flight.hashCode());
		result = prime * result
				+ ((flightKey == null) ? 0 : flightKey.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
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
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
