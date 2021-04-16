package it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest;

import it.unicam.cs.gp.inmytable.user.*;

public interface IMealRequest {

	User getHost();

	void setHost();

	User getHomeOwner();

}