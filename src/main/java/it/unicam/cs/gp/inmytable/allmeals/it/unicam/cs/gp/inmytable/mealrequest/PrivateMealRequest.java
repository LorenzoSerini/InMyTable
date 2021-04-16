package it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest;

import it.unicam.cs.gp.inmytable.notification.*;
import it.unicam.cs.gp.inmytable.user.*;

public class PrivateMealRequest extends MealRequest implements Notfication {

	private User homeOwner;

	@Override
	public User getHost() {
		return null;
	}

	@Override
	public void setHost() {

	}

	@Override
	public User getHomeOwner() {
		return null;
	}

	@Override
	public void attach() {

	}

	@Override
	public void detach() {

	}

	@Override
	public void notifyObservers() {

	}

	@Override
	public void accept() {

	}

	@Override
	public void refuse() {

	}

	@Override
	public NotificationStates getState() {
		return null;
	}
}