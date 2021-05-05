package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.notification.Notification;
import it.unicam.cs.gp.inmytable.notification.Subscription;
import it.unicam.cs.gp.inmytable.user.User;

import java.sql.SQLException;
import java.util.List;

public interface MealPersistence extends Persistence{
    /**
     * Register a Meal
     * @param meal  The meal
     */
    void registerMeal(Meal meal) throws SQLException;

    /**
     * Register a host to a meal
     * @param host The host
     * @param meal The meal
     * @param subscription The meal subscription
     */
    void registerUserToMeal(User host, Meal meal, Subscription subscription) throws SQLException;

    /**
     * Get all meals with all parameters
     * @return meals list
     */
    List<Meal> getMealsList() throws Exception;

    /**
     * Get all notifications
     * @return notifications list
     * @throws Exception
     */
    List<Notification> getNotificationsList() throws Exception;
}
