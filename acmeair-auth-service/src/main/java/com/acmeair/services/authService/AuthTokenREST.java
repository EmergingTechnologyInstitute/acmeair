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

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.acmeair.astyanax.service.CustomerServiceImpl;
import com.acmeair.entities.CustomerSession;
import com.acmeair.service.CustomerService;
import com.acmeair.service.TransactionService;

@Path("/authtoken")
@Component
public class AuthTokenREST {
	
	private CustomerService customerService = new CustomerServiceImpl();
	
	@Context 
	private HttpServletRequest request;
	private TransactionService transactionService = null; 
	private boolean initializedTXService = false;

	@POST
	@Path("/byuserid/{userid}")
	@Produces("application/json")
	public /* CustomerSession */ Response createToken(@PathParam("userid") String userid) {		
		CustomerSession cs = customerService.createSession(userid);
		return Response.ok(cs).build();
	}
	
	@GET
	@Path("{tokenid}")
	@Produces("application/json")
	public Response validateToken(@PathParam("tokenid") String tokenid) {		
		CustomerSession cs = customerService.validateSession(tokenid);
		if (cs == null) {
			throw new WebApplicationException(404);
		}
		else {
			return Response.ok(cs).build();
		}
	}
	
	@DELETE
	@Path("{tokenid}")
	@Produces("application/json")
	public Response invalidateToken(@PathParam("tokenid") String tokenid) {		
		customerService.invalidateSession(tokenid);
		return Response.ok().build();
	}
	
}
