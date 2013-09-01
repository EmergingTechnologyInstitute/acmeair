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
package com.acmeair.web;

import java.util.concurrent.atomic.AtomicReference;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class ServiceLocator {

	public static String REPOSITORY_LOOKUP_KEY = "com.acmeair.repository.type";
	
	final ApplicationContext ctx;
	private static Logger logger = LoggerFactory
			.getLogger(ServiceLocator.class);

	private static AtomicReference<ServiceLocator> singletonServiceLocator = new AtomicReference<ServiceLocator>();

	static ServiceLocator instance() {
		if (singletonServiceLocator.get() == null) {
			synchronized (singletonServiceLocator) {
				if (singletonServiceLocator.get() == null) {
					singletonServiceLocator.set(new ServiceLocator());
				}
			}
		}
		return singletonServiceLocator.get();
	}

	private ServiceLocator() {
		String type = null;
		String lookup = "com/acmeair/repository/type";
		javax.naming.Context context = null;
		javax.naming.Context envContext;
		try {
			context = new javax.naming.InitialContext();
			envContext = (javax.naming.Context) context.lookup("java:comp/env");
			if (envContext != null)
			{
				System.out.println("lookup:"+lookup);
				type = (String) envContext.lookup(lookup);
			}
		} catch (NamingException e) {
			System.out.println(e);
		}

		if (type != null) {
			logger.info("Found repository in web.xml:" + type);
		} else if (context != null) {
			try {
				System.out.println("lookup:"+lookup);
				type = (String) context.lookup(lookup);
				if (type != null)
					logger.info("Found repository in server.xml:" + type);
			} catch (NamingException e) {
				System.out.println(e);
			}
		}

		if (type == null) {
			type = System.getProperty(REPOSITORY_LOOKUP_KEY);
			if (type != null)
				logger.info("Found repository in jvm property:" + type);
			else {
				type = System.getenv(REPOSITORY_LOOKUP_KEY);
				if (type != null)
					logger.info("Found repository in environment property:"
							+ type);
			}
		}

		@SuppressWarnings("rawtypes")
		Class clazz;
		
		logger.info("Type is:"+type);

		if (type == null) {
			type = "astyanax";
		} 
		
		if (type.equals("astyanax")) {
			try {
				clazz = Class.forName("com.acmeair.web.config.AstyanaxDirectAppConfig");
			} catch (ClassNotFoundException e) {		
				clazz = null;
			}
		}  else {
			try {
				clazz = Class.forName("com.acmeair.web.config.AstyanaxDirectAppConfig");
			} catch (ClassNotFoundException e) {
				clazz = null;
			}			
		}
		logger.info("Using ApplicationConfig:"+clazz);
		ctx = new AnnotationConfigApplicationContext(clazz);
	}

	public static <T> T getService(Class<T> clazz) {
		return instance().ctx.getBean(clazz);
	}
}
