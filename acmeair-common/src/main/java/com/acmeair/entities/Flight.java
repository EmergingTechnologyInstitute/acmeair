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
package com.acmeair.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public interface Flight {

	String getFlightId();

	String getFlightSegmentId();

	Date getScheduledDepartureTime();

	void setScheduledDepartureTime(Date scheduledDepartureTime);

	Date getScheduledArrivalTime();

	void setScheduledArrivalTime(Date scheduledArrivalTime);

	BigDecimal getFirstClassBaseCost();

	void setFirstClassBaseCost(BigDecimal firstClassBaseCost);

	BigDecimal getEconomyClassBaseCost();

	void setEconomyClassBaseCost(BigDecimal economyClassBaseCost);

	int getNumFirstClassSeats();

	void setNumFirstClassSeats(int numFirstClassSeats);

	int getNumEconomyClassSeats();

	void setNumEconomyClassSeats(int numEconomyClassSeats);

	String getAirplaneTypeId();

	void setAirplaneTypeId(String airplaneTypeId);

	FlightSegment getFlightSegment();

	void setFlightSegment(FlightSegment flightSegment);

	void setId(String id);	
	
	Serializable getKey();
}