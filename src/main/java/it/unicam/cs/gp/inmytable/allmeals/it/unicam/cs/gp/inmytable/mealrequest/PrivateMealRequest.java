package it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest;

import it.unicam.cs.gp.inmytable.allmeals.meals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.MealStates;
import it.unicam.cs.gp.inmytable.allmeals.meals.PaymentType;
import it.unicam.cs.gp.inmytable.notification.*;
import it.unicam.cs.gp.inmytable.user.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class PrivateMealRequest extends MealRequest{


	public PrivateMealRequest(User host, String mealType, ConsumationType consumationType, PaymentType payment, String description, LocalDate date, LocalTime time, LocalDate expiryDate, LocalTime expiryTime, String price, String place, String allergy, int mealsNumber, User homeOwner) {
		super(host, mealType, consumationType, payment, description, date, time, expiryDate, expiryTime, price, place, allergy, mealsNumber);
		setHomeOwner(homeOwner);
	}


	@Override
	public void refuse(Observer<User> observer) {
		if (super.getNotificationState()!= NotificationStates.PENDING) throw new IllegalArgumentException("The Request is not pending");
		super.setState(MealStates.EXPIRED);
		super.setNotificationState(NotificationStates.REFUSED);
	}


}