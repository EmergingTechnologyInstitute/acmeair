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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.acmeair.entities.Flight;
import com.acmeair.entities.FlightSegment;

@Entity
public class FlightImpl implements Flight {

	@Id
	private String id;

	@Column(name="scheduledDepartureDate")
	private Date scheduledDepartureDate;

	@Column
	private Date scheduledArrivalTime;

	@Column
	private BigDecimal firstClassBaseCost;

	@Column
	private BigDecimal economyClassBaseCost;

	@Column
	private int numFirstClassSeats;

	@Column
	private int numEconomyClassSeats;

	@Column
	private String airplaneTypeId;
	
	@Column
	private String flightSegmentId;

	private transient FlightSegment flightSegment;
	
	public FlightImpl()
	{
		
	}

	public FlightImpl(String id, String flightSegmentId,
			Date scheduledDepartureDate, Date scheduledArrivalTime,
			BigDecimal firstClassBaseCost, BigDecimal economyClassBaseCost,
			int numFirstClassSeats, int numEconomyClassSeats,
			String airplaneTypeId) {
		this.id = id;
		this.scheduledDepartureDate = scheduledDepartureDate;
		this.scheduledArrivalTime = scheduledArrivalTime;
		this.firstClassBaseCost = firstClassBaseCost;
		this.economyClassBaseCost = economyClassBaseCost;
		this.numFirstClassSeats = numFirstClassSeats;
		this.numEconomyClassSeats = numEconomyClassSeats;
		this.airplaneTypeId = airplaneTypeId;
		this.flightSegmentId=flightSegmentId;
	}

	// The method is needed for index calculation
	@Override
	public String getFlightSegmentId() {
		return flightSegmentId;
	}

	@Override
	public Date getScheduledDepartureTime() {
		return scheduledDepartureDate;
	}

	@Override
	public void setScheduledDepartureTime(Date scheduledDepartureDate) {
		this.scheduledDepartureDate = scheduledDepartureDate;
	}

	@Override
	public Date getScheduledArrivalTime() {
		return scheduledArrivalTime;
	}

	@Override
	public void setScheduledArrivalTime(Date scheduledArrivalTime) {
		this.scheduledArrivalTime = scheduledArrivalTime;
	}

	@Override
	public BigDecimal getFirstClassBaseCost() {
		return firstClassBaseCost;
	}

	@Override
	public void setFirstClassBaseCost(BigDecimal firstClassBaseCost) {
		this.firstClassBaseCost = firstClassBaseCost;
	}

	@Override
	public BigDecimal getEconomyClassBaseCost() {
		return economyClassBaseCost;
	}

	@Override
	public void setEconomyClassBaseCost(BigDecimal economyClassBaseCost) {
		this.economyClassBaseCost = economyClassBaseCost;
	}

	@Override
	public int getNumFirstClassSeats() {
		return numFirstClassSeats;
	}

	@Override
	public void setNumFirstClassSeats(int numFirstClassSeats) {
		this.numFirstClassSeats = numFirstClassSeats;
	}

	@Override
	public int getNumEconomyClassSeats() {
		return numEconomyClassSeats;
	}

	@Override
	public void setNumEconomyClassSeats(int numEconomyClassSeats) {
		this.numEconomyClassSeats = numEconomyClassSeats;
	}

	@Override
	public String getAirplaneTypeId() {
		return airplaneTypeId;
	}

	@Override
	public void setAirplaneTypeId(String airplaneTypeId) {
		this.airplaneTypeId = airplaneTypeId;
	}

	@Override
	public FlightSegment getFlightSegment() {
		return flightSegment;
	}

	@Override
	public void setFlightSegment(FlightSegment flightSegment) {
		this.flightSegment = flightSegment;
	}

	@Override
	public String getFlightId() {
		return id;
	}

	@Override
	public void setId(final String id) {
		this.id = id;

	}

	@Override
	public String toString() {
		return "FlightImpl [id=" + id + ", scheduledDepartureDate="
				+ scheduledDepartureDate + ", scheduledArrivalTime="
				+ scheduledArrivalTime + ", firstClassBaseCost="
				+ firstClassBaseCost + ", economyClassBaseCost="
				+ economyClassBaseCost + ", numFirstClassSeats="
				+ numFirstClassSeats + ", numEconomyClassSeats="
				+ numEconomyClassSeats + ", airplaneTypeId=" + airplaneTypeId
				+ ", flightSegmentId=" + flightSegmentId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((airplaneTypeId == null) ? 0 : airplaneTypeId.hashCode());
		result = prime
				* result
				+ ((economyClassBaseCost == null) ? 0 : economyClassBaseCost
						.hashCode());
		result = prime
				* result
				+ ((firstClassBaseCost == null) ? 0 : firstClassBaseCost
						.hashCode());
		result = prime * result
				+ ((flightSegmentId == null) ? 0 : flightSegmentId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + numEconomyClassSeats;
		result = prime * result + numFirstClassSeats;
		result = prime
				* result
				+ ((scheduledArrivalTime == null) ? 0 : scheduledArrivalTime
						.hashCode());
		result = prime
				* result
				+ ((scheduledDepartureDate == null) ? 0
						: scheduledDepartureDate.hashCode());
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
		FlightImpl other = (FlightImpl) obj;
		if (airplaneTypeId == null) {
			if (other.airplaneTypeId != null)
				return false;
		} else if (!airplaneTypeId.equals(other.airplaneTypeId))
			return false;
		if (economyClassBaseCost == null) {
			if (other.economyClassBaseCost != null)
				return false;
		} else if (!economyClassBaseCost.equals(other.economyClassBaseCost))
			return false;
		if (firstClassBaseCost == null) {
			if (other.firstClassBaseCost != null)
				return false;
		} else if (!firstClassBaseCost.equals(other.firstClassBaseCost))
			return false;
		if (flightSegmentId == null) {
			if (other.flightSegmentId != null)
				return false;
		} else if (!flightSegmentId.equals(other.flightSegmentId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (numEconomyClassSeats != other.numEconomyClassSeats)
			return false;
		if (numFirstClassSeats != other.numFirstClassSeats)
			return false;
		if (scheduledArrivalTime == null) {
			if (other.scheduledArrivalTime != null)
				return false;
		} else if (!scheduledArrivalTime.equals(other.scheduledArrivalTime))
			return false;
		if (scheduledDepartureDate == null) {
			if (other.scheduledDepartureDate != null)
				return false;
		} else if (!scheduledDepartureDate.equals(other.scheduledDepartureDate))
			return false;
		return true;
	}

	@Override
	public Serializable getKey() {
		return id;
	}


}