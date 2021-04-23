package it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest;

import it.unicam.cs.gp.inmytable.allmeals.meals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.MealStates;
import it.unicam.cs.gp.inmytable.allmeals.meals.PaymentType;
import it.unicam.cs.gp.inmytable.notification.*;
import it.unicam.cs.gp.inmytable.user.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class PrivateMealRequest extends MealRequest implements Notification {

	private User homeOwner;
	private List<Observer> observers;
	private NotificationStates notificationState;

	public PrivateMealRequest(User host, String mealType, ConsumationType consumationType, PaymentType payment, String description, LocalDate date, LocalTime time, LocalDate expiryDate, LocalTime expiryTime, String price, String place, String allergy, int mealsNumber, User homeOwner) {
		super(host, mealType, consumationType, payment, description, date, time, expiryDate, expiryTime, price, place, allergy, mealsNumber);
		setHomeOwner(homeOwner);
	}


	@Override
	public void attach(Observer observer) {
		this.observers.add(observer);

	}

	@Override
	public void detach(Observer observer) {
		this.observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for (Observer o: observers) {
			o.update();
		}

	}

	@Override
	public void accept() {
		if (notificationState != NotificationStates.PENDING) throw new IllegalArgumentException("The Request is not pending");
		super.setState(MealStates.FULL);
		this.notificationState = NotificationStates.ACCEPTED;

	}

	@Override
	public void refuse() {
		if (notificationState != NotificationStates.PENDING) throw new IllegalArgumentException("The Request is not pending");
		super.setState(MealStates.EXPIRED);
		this.notificationState = NotificationStates.REFUSED;
	}

	@Override
	public NotificationStates getNotificationState() {
		return notificationState;
	}
}