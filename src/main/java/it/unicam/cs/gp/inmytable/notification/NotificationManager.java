package it.unicam.cs.gp.inmytable.notification;

import it.unicam.cs.gp.inmytable.user.*;

import java.util.Set;

/**
 * Class who manage the notification of one user
 */
public class NotificationManager implements Observer {

	private User user;
	private Set<Notification> notificationSet;

	/**
	 * Build a new NotificationManager for the user
	 * @param user	who build the notificationManager
	 */
	public NotificationManager(User user){
		this.user = user;
	}

	/**
	 * Questo metodo aggiorna la view
	 */
	public void update() {
		// TODO - implement NotificationManager.update
		throw new UnsupportedOperationException();
	}

	/**
	 * Add a new notification to the set of the notification
	 * @param notification new notification to add
	 */
	public void addNotification(Notification notification){
		this.notificationSet.add(notification);
	}

}