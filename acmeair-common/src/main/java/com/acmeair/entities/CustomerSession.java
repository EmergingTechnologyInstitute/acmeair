package com.acmeair.entities;

import java.util.Date;

public interface CustomerSession {

	public abstract String getId();

	public abstract void setId(String id);

	public abstract String getCustomerid();

	public abstract void setCustomerid(String customerid);

	public abstract Date getLastAccessedTime();

	public abstract void setLastAccessedTime(Date lastAccessedTime);

	public abstract Date getTimeoutTime();

	public abstract void setTimeoutTime(Date timeoutTime);

}