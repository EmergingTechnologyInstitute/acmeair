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
package com.acmeair.astyanax.service;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.acmeair.astyanax.entities.CustomerImpl;
import com.acmeair.astyanax.entities.CustomerSessionImpl;
import com.acmeair.astyanax.entities.EntityManagers;
import com.acmeair.entities.Customer;
import com.acmeair.entities.CustomerAddress;
import com.acmeair.entities.CustomerSession;
import com.acmeair.entities.impl.MemberShipStatus;
import com.acmeair.entities.impl.PhoneType;
import com.acmeair.service.CustomerService;
import com.acmeair.service.KeyGenerator;
import com.netflix.astyanax.entitystore.EntityManager;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

	private static final int DAYS_TO_ALLOW_SESSION = 1;

	@Resource
	KeyGenerator keyGenerator;

	@Override
	public Customer createCustomer(String username, String password,
			MemberShipStatus status, int total_miles, int miles_ytd,
			String phoneNumber, PhoneType phoneNumberType,
			CustomerAddress address) {
		try {
			CustomerImpl customer = new CustomerImpl(username, password,
					status, total_miles, miles_ytd, address, phoneNumber,
					phoneNumberType);
			EntityManager<CustomerImpl, String> entityManager = EntityManagers.getCustomerEntityManager();
			entityManager.put(customer);
			return customer;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		try {			
			EntityManager<CustomerImpl, String> entityManager = EntityManagers.getCustomerEntityManager();
			entityManager.put((CustomerImpl)customer);
			return customer;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Customer getCustomer(String username) {
		try {
			EntityManager<CustomerImpl, String> entityManager = EntityManagers.getCustomerEntityManager();
			CustomerImpl c = entityManager.get(username);
			return c;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Customer getCustomerByUsername(String username) {
		Customer c = getCustomer(username);
		if (c != null) {
			c.setPassword(null);
		}
		return c;
	}

	@Override
	public boolean validateCustomer(String username, String password) {
		boolean validatedCustomer = false;
		Customer customerToValidate = getCustomer(username);
		if (customerToValidate != null) {
			validatedCustomer = password.equals(customerToValidate
					.getPassword());
		}
		return validatedCustomer;
	}

	@Override
	public Customer getCustomerByUsernameAndPassword(String username,
			String password) {
		Customer c = getCustomer(username);
		if (!c.getPassword().equals(password)) {
			return null;
		}
		// Should we also set the password to null?
		return c;
	}

	@Override
	public CustomerSession validateSession(String sessionid) {
		try {
			EntityManager<CustomerSessionImpl, String> entityManager = EntityManagers.getCustomerSessionEntityManager();
			CustomerSessionImpl cSession = entityManager.get(sessionid);
			if (cSession == null) {
				return null;
			}

			Date now = new Date();

			if (cSession.getTimeoutTime().before(now)) {
				entityManager.delete(sessionid);
				return null;
			}
			return cSession;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public CustomerSession createSession(String customerId) {
		try {
			String sessionId = keyGenerator.generate().toString();
			Date now = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(now);
			c.add(Calendar.DAY_OF_YEAR, DAYS_TO_ALLOW_SESSION);
			Date expiration = c.getTime();
			CustomerSessionImpl cSession = new CustomerSessionImpl(sessionId,
					customerId, now, expiration);
			EntityManager<CustomerSessionImpl, String> entityManager = EntityManagers.getCustomerSessionEntityManager();
			entityManager.put(cSession);
			return cSession;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void invalidateSession(String sessionid) {
		try {
			EntityManager<CustomerSessionImpl, String> entityManager = EntityManagers.getCustomerSessionEntityManager();
			entityManager.delete(sessionid);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
