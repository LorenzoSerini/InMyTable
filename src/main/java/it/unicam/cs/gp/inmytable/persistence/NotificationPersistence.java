package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.allmeals.Food;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.IMealRequest;

import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
import it.unicam.cs.gp.inmytable.notification.INotification;
import it.unicam.cs.gp.inmytable.notification.SimpleNotification;
import it.unicam.cs.gp.inmytable.notification.SubscriptionNotification;
import it.unicam.cs.gp.inmytable.user.IUser;

import java.sql.SQLException;
import java.util.List;

public interface NotificationPersistence {

    List<SubscriptionNotification<IUser, Food>> getSubscriptionNotifications();

    List<SubscriptionNotification<IUser, IMealRequest>> getMealRequestNotifications(IUser user);

    List<SubscriptionNotification<IUser, IMeal>> getMealNotifications(IUser user);

    List<SimpleNotification<IUser>> getSimpleNotifications(IUser user);

    void registerSimpleNotification(SimpleNotification<?> notification) throws Exception;
}
