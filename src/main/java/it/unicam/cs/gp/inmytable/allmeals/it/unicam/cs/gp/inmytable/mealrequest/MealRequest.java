package it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest;

import it.unicam.cs.gp.inmytable.user.*;

import java.util.Date;

public abstract class MealRequest implements IMealRequest {

	private Date date;
	private Date expiryDate;
	private String place;
	private User host;
	private int homeOwner;

}