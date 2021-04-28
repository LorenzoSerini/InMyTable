package it.unicam.cs.gp.inmytable.notification;

import it.unicam.cs.gp.inmytable.user.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class who manage the notification of one user
 */
public class NotificationManager implements Observer {

	private User user;
	private List<Notification> notificationSet;

	/**
	 * Build a new NotificationManager for the user
	 * @param user	who build the notificationManager
	 */
	public NotificationManager(User user){
		this.user = user;
		this.notificationSet = new ArrayList<>();
	}

	/**
	 * Questo metodo aggiorna la view
	 */
	public void update() {

	}

	/**
	 * Add a new notification to the set of the notification
	 * @param notification new notification to add
	 */
	public void addNotification(Notification notification){
		this.notificationSet.add(notification);
	}

	public List<Notification> getNotificationSet(){
		return notificationSet;
	}
}