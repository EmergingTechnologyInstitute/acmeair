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
package com.acmeair.wxs.service;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acmeair.entities.Customer;
import com.acmeair.entities.CustomerAddress;
import com.acmeair.entities.CustomerSession;
import com.acmeair.entities.impl.MemberShipStatus;
import com.acmeair.entities.impl.PhoneType;
import com.acmeair.service.CustomerService;
import com.acmeair.service.CustomerServiceLoader;
import com.acmeair.service.KeyGenerator;
import com.acmeair.wxs.entities.CustomerImpl;
import com.acmeair.wxs.entities.CustomerSessionImpl;
import com.acmeair.wxs.utils.WXSSessionManager;
import com.ibm.websphere.objectgrid.ObjectMap;
import com.ibm.websphere.objectgrid.Session;

@Service("wxsCustomerService")
public class CustomerServiceImpl implements CustomerService, CustomerServiceLoader{
	
	private static final String CUSTOMER_MAP_NAME="Customer";
	private static final String CUSTOMER_SESSION_MAP_NAME="CustomerSession";
	
	private static final int DAYS_TO_ALLOW_SESSION = 1;
	
	@Autowired
	private WXSSessionManager sessionManager;

	
	@Resource
	KeyGenerator keyGenerator;

	@Override
	public CustomerImpl createCustomer(String username, String password,
			MemberShipStatus status, int total_miles, int miles_ytd,
			String phoneNumber, PhoneType phoneNumberType,
			CustomerAddress address) {
		try{
			CustomerImpl customer = new CustomerImpl(username, password, status, total_miles, miles_ytd, address, phoneNumber, phoneNumberType);
			Session session = sessionManager.getObjectGridSession();
			ObjectMap customerMap = session.getMap(CUSTOMER_MAP_NAME);
			customerMap.insert(customer.getUsername(), customer);
			return customer;
		}catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public CustomerImpl updateCustomer(Customer customer) {
		try{
			Session session = sessionManager.getObjectGridSession();
			ObjectMap customerMap = session.getMap(CUSTOMER_MAP_NAME);
			customerMap.update(customer.getUsername(), customer);
			return (CustomerImpl) customer;
		}catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private CustomerImpl getCustomer(String username) {
		try{
			Session session = sessionManager.getObjectGridSession();
			ObjectMap customerMap = session.getMap(CUSTOMER_MAP_NAME);
			
			CustomerImpl c = (CustomerImpl) customerMap.get(username);
			return c;
		}catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public CustomerImpl getCustomerByUsername(String username) {
		CustomerImpl c = getCustomer(username);
		if (c != null) {
			c.setPassword(null);
		}
		return c;
	}

	@Override
	public boolean validateCustomer(String username, String password) {
		boolean validatedCustomer = false;
		CustomerImpl customerToValidate = getCustomer(username);
		if (customerToValidate != null) {
			validatedCustomer = password.equals(customerToValidate.getPassword());
		}
		return validatedCustomer;
	}

	@Override
	public CustomerImpl getCustomerByUsernameAndPassword(String username,
			String password) {
		CustomerImpl c = getCustomer(username);
		if (!c.getPassword().equals(password)) {
			return null;
		}
		// Should we also set the password to null?
		return c;
	}

	@Override
	public CustomerSession validateSession(String sessionid) {
		try{
			Session session = sessionManager.getObjectGridSession();
			ObjectMap customerSessionMap = session.getMap(CUSTOMER_SESSION_MAP_NAME);
			
			CustomerSession cSession = (CustomerSession)customerSessionMap.get(sessionid);
			if (cSession == null) {
				return null;
			}
			
			Date now = new Date();
			
			if (cSession.getTimeoutTime().before(now)) {
				customerSessionMap.remove(sessionid);
				return null;
			}
			return cSession;
		}catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public CustomerSession createSession(String customerId) {
		try{
			String sessionId = keyGenerator.generate().toString();
			Date now = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(now);
			c.add(Calendar.DAY_OF_YEAR, DAYS_TO_ALLOW_SESSION);
			Date expiration = c.getTime();
			CustomerSession cSession = new CustomerSessionImpl(sessionId, customerId, now, expiration);
			Session session = sessionManager.getObjectGridSession();
			ObjectMap customerSessionMap = session.getMap(CUSTOMER_SESSION_MAP_NAME);
			customerSessionMap.insert(cSession.getId(), cSession);
			return cSession;
		}catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public void invalidateSession(String sessionid) {
		try{
			Session session = sessionManager.getObjectGridSession();
			ObjectMap customerSessionMap = session.getMap(CUSTOMER_SESSION_MAP_NAME);
			customerSessionMap.remove(sessionid);
		}catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
