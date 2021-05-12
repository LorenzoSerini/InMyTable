package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.notification.SubscriptionNotification;
import it.unicam.cs.gp.inmytable.user.User;

import java.sql.SQLException;
import java.util.List;

public interface MealPersistence extends Persistence{
    /**
     * Register a Meal
     * @param meal  The meal
     */
    void registerMeal(Meal meal) throws Exception;

    /**
     * Register a host to a meal
     * @param host The host
     * @param meal The meal
     */
    void registerUserToMeal(User host, Meal meal, SubscriptionNotification<?,?> notification) throws Exception;

    /**
     * Get all meals with all parameters
     * @return meals list
     */
    List<Meal> getMealsList() throws Exception;


}
