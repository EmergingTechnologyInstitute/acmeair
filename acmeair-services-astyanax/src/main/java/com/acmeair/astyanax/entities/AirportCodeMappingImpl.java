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

import com.acmeair.entities.AirportCodeMapping;

@Entity
public class AirportCodeMappingImpl implements AirportCodeMapping{

	@Id
	private String airportCode;
	
	@Column
	private String airportName;
	
	public AirportCodeMappingImpl() {
	}
	
	public AirportCodeMappingImpl(String airportCode, String airportName) {
		this.airportCode = airportCode;
		this.airportName = airportName;
	}
	
	/* (non-Javadoc)
	 * @see com.acmeair.entities.impl.AirportCodeMapping#getAirportCode()
	 */
	@Override
	public String getAirportCode() {
		return airportCode;
	}
	
	/* (non-Javadoc)
	 * @see com.acmeair.entities.impl.AirportCodeMapping#setAirportCode(java.lang.String)
	 */
	@Override
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	
	/* (non-Javadoc)
	 * @see com.acmeair.entities.impl.AirportCodeMapping#getAirportName()
	 */
	@Override
	public String getAirportName() {
		return airportName;
	}
	
	/* (non-Javadoc)
	 * @see com.acmeair.entities.impl.AirportCodeMapping#setAirportName(java.lang.String)
	 */
	@Override
	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

}