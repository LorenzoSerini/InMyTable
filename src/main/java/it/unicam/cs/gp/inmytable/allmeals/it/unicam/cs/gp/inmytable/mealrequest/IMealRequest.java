package it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest;

import it.unicam.cs.gp.inmytable.allmeals.meals.MealStates;
import it.unicam.cs.gp.inmytable.user.*;

import java.time.LocalDate;
import java.time.LocalTime;

public interface IMealRequest {

	User getHost();

	String getMealType();

	void setHost(User host);

	User getHomeOwner();

	void setDate(LocalDate date);

	LocalDate getDate();

	void setTime(LocalTime time);

	LocalTime getTime();

	void setExpiringDate(LocalDate expiryDate);

	LocalDate getExpiryDate();

	void setExpiryTime(LocalTime expiryTime);

	LocalTime getExpiryTime();

	void setDescription(String description);

	String getDescription();

	String getAllergy();

	int getMealsNumber();

	MealStates getState();

	void setHomeOwner(User homeOwner);



}