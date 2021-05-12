package it.unicam.cs.gp.inmytable.allmeals.mealrequest;

import it.unicam.cs.gp.inmytable.allmeals.meals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.Food;
import it.unicam.cs.gp.inmytable.allmeals.meals.MealStates;
import it.unicam.cs.gp.inmytable.allmeals.meals.PaymentType;
import it.unicam.cs.gp.inmytable.user.*;

import java.time.LocalDate;
import java.time.LocalTime;

public interface IMealRequest extends Food {

	IUser getHost();

	void setHost(IUser host);

	String getAllergy();

	int getMealsNumber();

	ConsumationType getConsummationType();

	MealRequestType getType();

	void setHomeOwner(IUser homeOwner);

}