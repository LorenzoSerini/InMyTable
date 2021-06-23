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

    /**
     * returns a subscription notification list
     * @return subscription notification list
     */
    List<SubscriptionNotification<IUser, Food>> getSubscriptionNotifications();

    /**
     * returns user meal request notifications
     * @param user the user
     * @return user meal request notifications
     */
    List<SubscriptionNotification<IUser, IMealRequest>> getMealRequestNotifications(IUser user);

    /**
     * returns user meal notifications
     * @param user the user
     * @return user meal notifications
     */
    List<SubscriptionNotification<IUser, IMeal>> getMealNotifications(IUser user);

    /**
     * returns user simple notification
     * @param user the user
     * @return user simple notification
     */
    List<SimpleNotification<IUser>> getSimpleNotifications(IUser user);

    /**
     * registers simple notification
     * @param notification the notification
     * @throws Exception
     */
    void registerSimpleNotification(SimpleNotification<?> notification) throws Exception;
}
