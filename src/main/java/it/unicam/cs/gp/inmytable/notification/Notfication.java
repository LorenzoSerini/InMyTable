package it.unicam.cs.gp.inmytable.notification;

public interface Notfication {

	void attach();

	void detach();

	void notifyObservers();

	void accept();

	void refuse();

	NotificationStates getState();

}