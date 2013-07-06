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
import java.util.*;

import com.acmeair.entities.CustomerSession;

public class CustomerSessionImpl implements Serializable, CustomerSession {
	
	private static final long serialVersionUID = 1L;

	private String id;
	private String customerid;
	private Date lastAccessedTime;
	private Date timeoutTime;
	
	public CustomerSessionImpl() {
	}

	public CustomerSessionImpl(String id, String customerid, Date lastAccessedTime,	Date timeoutTime) {
		this.id= id;
		this.customerid = customerid;
		this.lastAccessedTime = lastAccessedTime;
		this.timeoutTime = timeoutTime;
	}
	

	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerSessionInterface#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerSessionInterface#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerSessionInterface#getCustomerid()
	 */
	@Override
	public String getCustomerid() {
		return customerid;
	}

	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerSessionInterface#setCustomerid(java.lang.String)
	 */
	@Override
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerSessionInterface#getLastAccessedTime()
	 */
	@Override
	public Date getLastAccessedTime() {
		return lastAccessedTime;
	}

	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerSessionInterface#setLastAccessedTime(java.util.Date)
	 */
	@Override
	public void setLastAccessedTime(Date lastAccessedTime) {
		this.lastAccessedTime = lastAccessedTime;
	}

	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerSessionInterface#getTimeoutTime()
	 */
	@Override
	public Date getTimeoutTime() {
		return timeoutTime;
	}

	/* (non-Javadoc)
	 * @see com.acmeair.entities.CustomerSessionInterface#setTimeoutTime(java.util.Date)
	 */
	@Override
	public void setTimeoutTime(Date timeoutTime) {
		this.timeoutTime = timeoutTime;
	}

	@Override
	public String toString() {
		return "CustomerSession [id=" + id + ", customerid=" + customerid
				+ ", lastAccessedTime=" + lastAccessedTime + ", timeoutTime="
				+ timeoutTime + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerSessionImpl other = (CustomerSessionImpl) obj;
		if (customerid == null) {
			if (other.customerid != null)
				return false;
		} else if (!customerid.equals(other.customerid))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastAccessedTime == null) {
			if (other.lastAccessedTime != null)
				return false;
		} else if (!lastAccessedTime.equals(other.lastAccessedTime))
			return false;
		if (timeoutTime == null) {
			if (other.timeoutTime != null)
				return false;
		} else if (!timeoutTime.equals(other.timeoutTime))
			return false;
		return true;
	}


	
}