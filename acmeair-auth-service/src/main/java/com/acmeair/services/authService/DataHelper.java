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
package com.acmeair.services.authService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.acmeair.astyanax.service.AstyanaxContextHelper;
import com.netflix.astyanax.Keyspace;

public class DataHelper implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// Do Nothing

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
	
		//Force connection to datasource, before first request 
		
		System.out.println(" Getting Keyspace!!!!!");
	
		AstyanaxContextHelper ach = new AstyanaxContextHelper();
		Keyspace keyspace = ach.getKeyspace();
		
		System.out.println(" Got keyspace "+keyspace);

	}

}
