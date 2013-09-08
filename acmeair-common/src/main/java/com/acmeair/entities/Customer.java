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

import com.acmeair.entities.impl.MemberShipStatus;
import com.acmeair.entities.impl.PhoneType;

public interface Customer {

	public abstract String getUsername();

	public abstract void setUsername(String username);

	public abstract String getPassword();

	public abstract void setPassword(String password);

	public abstract MemberShipStatus getStatus();

	public abstract void setStatus(MemberShipStatus status);

	public abstract int getTotal_miles();

	public abstract void setTotal_miles(int total_miles);

	public abstract int getMiles_ytd();

	public abstract void setMiles_ytd(int miles_ytd);

	public abstract String getPhoneNumber();

	public abstract void setPhoneNumber(String phoneNumber);

	public abstract PhoneType getPhoneNumberType();

	public abstract void setPhoneNumberType(PhoneType phoneNumberType);

	public abstract CustomerAddress getAddress();

	public abstract void setAddress(CustomerAddress address);

}