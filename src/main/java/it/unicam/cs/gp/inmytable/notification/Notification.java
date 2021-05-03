package it.unicam.cs.gp.inmytable.notification;

public interface Notification<E> {

	void attach(Observer<E> observer);

	void detach(Observer<E> observer);

	void notifyObservers();

	void accept(Observer<E> observer) throws Exception;

	void refuse(Observer<E> observer) throws Exception;

	NotificationStates getNotificationState();

	void setNotificationState(NotificationStates state);

}