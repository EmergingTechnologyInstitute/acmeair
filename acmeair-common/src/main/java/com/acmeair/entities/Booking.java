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

import java.util.Date;

public interface Booking {
	
	public abstract String getBookingId();

	public abstract void setFlight(Flight flight);

	public abstract Date getDateOfBooking();

	public abstract void setDateOfBooking(Date dateOfBooking);

	public abstract Customer getCustomer();

	public abstract Flight getFlight();

}