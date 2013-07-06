package com.acmeair.service;

import com.acmeair.entities.Customer;
import com.acmeair.entities.CustomerAddress;
import com.acmeair.entities.impl.MemberShipStatus;
import com.acmeair.entities.impl.PhoneType;

public interface CustomerServiceLoader {
	
	public Customer createCustomer(
			String username, String password, MemberShipStatus status, int total_miles,
			int miles_ytd, String phoneNumber, PhoneType phoneNumberType, CustomerAddress address);

}
