package it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest;

import it.unicam.cs.gp.inmytable.allmeals.meals.MealStates;
import it.unicam.cs.gp.inmytable.notification.*;
import it.unicam.cs.gp.inmytable.user.*;

import java.util.Date;
import java.util.List;

public class PrivateMealRequest extends MealRequest implements Notification {

	private User homeOwner;
	private List<Observer> observers;
	private NotificationStates state;

	public PrivateMealRequest(User host, Date date, Date expiringDate,String place, User homeOwner) {
		super(host,date, expiringDate, place);
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
		if (state != NotificationStates.PENDING) throw new IllegalArgumentException("The Request is not pending");
		super.setState(MealStates.FULL);
		this.state = NotificationStates.ACCEPTED;

	}

	@Override
	public void refuse() {
		if (state != NotificationStates.PENDING) throw new IllegalArgumentException("The Request is not pending");
		this.state = NotificationStates.REFUSED;

	}

	@Override
	public NotificationStates getState() {
		return state;
	}
}