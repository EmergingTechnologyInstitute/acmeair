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

import com.acmeair.entities.Customer;
import com.acmeair.entities.CustomerAddress;
import com.acmeair.entities.impl.MemberShipStatus;
import com.acmeair.entities.impl.PhoneType;

public class CustomerImpl implements Serializable, Customer{
	
	private static final long serialVersionUID = 1L;

	private String id;
	private String password;
	private MemberShipStatus status;
	private int total_miles;
	private int miles_ytd;

	private CustomerAddress address;
	private String phoneNumber;
	private PhoneType phoneNumberType;
	
	public CustomerImpl() {
	}
	
	public CustomerImpl(String username, String password, MemberShipStatus status, int total_miles, int miles_ytd, CustomerAddress address, String phoneNumber, PhoneType phoneNumberType) {
		this.id = username;
		this.password = password;
		this.status = status;
		this.total_miles = total_miles;
		this.miles_ytd = miles_ytd;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.phoneNumberType = phoneNumberType;
	}

	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerInterface#getUsername()
	 */
	@Override
	public String getUsername() {
		return id;
	}
	
	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerInterface#setUsername(java.lang.String)
	 */
	@Override
	public void setUsername(String username) {
		this.id = username;
	}
	
	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerInterface#getPassword()
	 */
	@Override
	public String getPassword() {
		return password;
	}
	
	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerInterface#setPassword(java.lang.String)
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
	}
	
	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerInterface#getStatus()
	 */
	@Override
	public MemberShipStatus getStatus() {
		return status;
	}
	
	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerInterface#setStatus(com.acmeair.entities.Customer.MemberShipStatus)
	 */
	@Override
	public void setStatus(MemberShipStatus status) {
		this.status = status;
	}
	
	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerInterface#getTotal_miles()
	 */
	@Override
	public int getTotal_miles() {
		return total_miles;
	}
	
	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerInterface#setTotal_miles(int)
	 */
	@Override
	public void setTotal_miles(int total_miles) {
		this.total_miles = total_miles;
	}
	
	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerInterface#getMiles_ytd()
	 */
	@Override
	public int getMiles_ytd() {
		return miles_ytd;
	}
	
	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerInterface#setMiles_ytd(int)
	 */
	@Override
	public void setMiles_ytd(int miles_ytd) {
		this.miles_ytd = miles_ytd;
	}
	
	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerInterface#getPhoneNumber()
	 */
	@Override
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerInterface#setPhoneNumber(java.lang.String)
	 */
	@Override
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerInterface#getPhoneNumberType()
	 */
	@Override
	public PhoneType getPhoneNumberType() {
		return phoneNumberType; 
	}

	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerInterface#setPhoneNumberType(com.acmeair.entities.Customer.PhoneType)
	 */
	@Override
	public void setPhoneNumberType(PhoneType phoneNumberType) {
		this.phoneNumberType = phoneNumberType;
	}

	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerInterface#getAddress()
	 */
	@Override
	public CustomerAddress getAddress() {
		return address;
	}

	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerInterface#setAddress(com.acmeair.entities.CustomerAddress)
	 */
	@Override
	public void setAddress(CustomerAddress address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", password=" + password + ", status="
				+ status + ", total_miles=" + total_miles + ", miles_ytd="
				+ miles_ytd + ", address=" + address + ", phoneNumber="
				+ phoneNumber + ", phoneNumberType=" + phoneNumberType + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerImpl other = (CustomerImpl) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (miles_ytd != other.miles_ytd)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (phoneNumberType != other.phoneNumberType)
			return false;
		if (status != other.status)
			return false;
		if (total_miles != other.total_miles)
			return false;
		return true;
	}

}
