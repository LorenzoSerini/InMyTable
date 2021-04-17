package it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest;

import it.unicam.cs.gp.inmytable.user.*;

import java.util.Date;

public interface IMealRequest {

	public User getHost();

	public void setHost(User host);

	public User getHomeOwner();

	public void setDate(Date date);

	public void setExpiringDate(Date expiringDate);

	public void setHomeOwner(User homeOwner);

}