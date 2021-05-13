package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.allmeals.meals.Food;
import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
import it.unicam.cs.gp.inmytable.notification.INotification;
import it.unicam.cs.gp.inmytable.notification.SimpleNotification;
import it.unicam.cs.gp.inmytable.notification.SubscriptionNotification;
import it.unicam.cs.gp.inmytable.user.IUser;

import java.util.List;

public interface NotificationPersistence {

    List<SubscriptionNotification<IUser, Food>> getSubscriptionNotifications();

    List<SimpleNotification<IUser>> getSimpleNotifications();
}
