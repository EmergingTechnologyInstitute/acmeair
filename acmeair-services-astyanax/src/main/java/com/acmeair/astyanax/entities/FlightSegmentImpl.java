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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.acmeair.entities.FlightSegment;

@Entity
public class FlightSegmentImpl implements FlightSegment{

	@Id
	private String id;
	
	@Column(name="originPort")
	private String originPort;
	
	@Column(name="destPort")
	private String destPort;
	
	@Column
	private int miles;

	public FlightSegmentImpl() {
	}
	
	public FlightSegmentImpl(String id, String origPort, String destPort, int miles) {
		this.id = id;
		this.originPort = origPort;
		this.destPort = destPort;
		this.miles = miles;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOriginPort() {
		return originPort;
	}

	public void setOriginPort(String originPort) {
		this.originPort = originPort;
	}

	public String getDestPort() {
		return destPort;
	}

	public void setDestPort(String destPort) {
		this.destPort = destPort;
	}

	public int getMiles() {
		return miles;
	}

	public void setMiles(int miles) {
		this.miles = miles;
	}

	

	@Override
	public String toString() {
		return "FlightSegmentImpl [id=" + id + ", originPort=" + originPort
				+ ", destPort=" + destPort + ", miles=" + miles + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlightSegmentImpl other = (FlightSegmentImpl) obj;
		if (destPort == null) {
			if (other.destPort != null)
				return false;
		} else if (!destPort.equals(other.destPort))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (miles != other.miles)
			return false;
		if (originPort == null) {
			if (other.originPort != null)
				return false;
		} else if (!originPort.equals(other.originPort))
			return false;
		return true;
	}
	
	
}