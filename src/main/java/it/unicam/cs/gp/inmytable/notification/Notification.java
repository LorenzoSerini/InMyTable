package it.unicam.cs.gp.inmytable.notification;

public interface Notification {

	void attach(Observer observer);

	void detach(Observer observer);

	void notifyObservers();

	void accept();

	void refuse();

	NotificationStates getState();

}