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
import java.math.BigDecimal;
import java.util.Date;

import com.acmeair.entities.Flight;
import com.acmeair.entities.FlightSegment;

public class FlightImpl implements Serializable, Flight {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private FlightPartitionableKey _id;
	
	private FlightPartitionableKey pkey;
	private Date scheduledDepartureTime;
	private Date scheduledArrivalTime;
	private BigDecimal firstClassBaseCost;
	private BigDecimal economyClassBaseCost;
	private int numFirstClassSeats;
	private int numEconomyClassSeats;
	private String airplaneTypeId;
	private String id;
	
	private FlightSegment flightSegment;
	
	public FlightImpl() {
	}
	
	public FlightImpl(String id, String flightSegmentId,
			Date scheduledDepartureTime, Date scheduledArrivalTime,
			BigDecimal firstClassBaseCost, BigDecimal economyClassBaseCost,
			int numFirstClassSeats, int numEconomyClassSeats,
			String airplaneTypeId) {
		this.pkey = new FlightPartitionableKey(flightSegmentId,id);
		this._id = this.pkey;
		this.id = id;
		this.scheduledDepartureTime = scheduledDepartureTime;
		this.scheduledArrivalTime = scheduledArrivalTime;
		this.firstClassBaseCost = firstClassBaseCost;
		this.economyClassBaseCost = economyClassBaseCost;
		this.numFirstClassSeats = numFirstClassSeats;
		this.numEconomyClassSeats = numEconomyClassSeats;
		this.airplaneTypeId = airplaneTypeId;
	}

	public FlightPartitionableKey getPkey() {
		return pkey;
	}
	
	@Override
	public String geFlightId()
	{
		return id;
	}


	/* (non-Javadoc)
	 * @see com.acmeair.entities.Delme#setPkey(com.acmeair.entities.FlightKey)
	 */
	public void setPkey(FlightPartitionableKey pkey) {
		this.pkey = pkey;
	}

	// The method is needed for index calculation
	@Override
	public String getFlightSegmentId()
	{
		return pkey.getFlightSegmentId();
	}
	
	@Override
	public Date getScheduledDepartureDate() {
		return scheduledDepartureTime;
	}


	@Override
	public void setScheduledDepartureDate(Date scheduledDepartureTime) {
		this.scheduledDepartureTime = scheduledDepartureTime;
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
	public void setId(final String id)
	{
		this.id = id;
	}

	
	public String toString() {
		return "Flight key="+pkey
				+ ", scheduledDepartureTime=" + scheduledDepartureTime
				+ ", scheduledArrivalTime=" + scheduledArrivalTime
				+ ", firstClassBaseCost=" + firstClassBaseCost
				+ ", economyClassBaseCost=" + economyClassBaseCost
				+ ", numFirstClassSeats=" + numFirstClassSeats
				+ ", numEconomyClassSeats=" + numEconomyClassSeats
				+ ", airplaneTypeId=" + airplaneTypeId + "]";
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
		if (flightSegment == null) {
			if (other.flightSegment != null)
				return false;
		} else if (!flightSegment.equals(other.flightSegment))
			return false;
		if (pkey == null) {
			if (other.pkey != null)
				return false;
		} else if (!pkey.equals(other.pkey))
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
		if (scheduledDepartureTime == null) {
			if (other.scheduledDepartureTime != null)
				return false;
		} else if (!scheduledDepartureTime.equals(other.scheduledDepartureTime))
			return false;
		return true;
	}
	
	
}