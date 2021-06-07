package it.unicam.cs.gp.inmytable.allmeals.mealrequest;

import it.unicam.cs.gp.inmytable.allmeals.Food;
import it.unicam.cs.gp.inmytable.user.*;


public interface IMealRequest extends Food {

	IUser getHost();

	void setHost(IUser host);

	String getAllergy();

	int getMealsNumber();


	MealRequestType getType();

	void setHomeOwner(IUser homeOwner);

}