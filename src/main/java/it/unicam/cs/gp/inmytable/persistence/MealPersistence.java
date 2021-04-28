package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.notification.Subscription;
import it.unicam.cs.gp.inmytable.user.User;

import java.sql.SQLException;
import java.util.List;

public interface MealPersistence {
    /**
     * Register a Meal
     * @param homeOwner The meal homeOwner
     * @param meal  The meal
     */
    void registerMeal(User homeOwner, Meal meal) throws SQLException;

    /**
     * Register a host to a meal
     * @param host The host
     * @param meal The meal
     * @param subscription The meal subscription
     */
    void registerUserToMeal(User host, Meal meal, Subscription subscription);

    /**
     * Get all meals with all parameters
     * @return meals list
     */
    List<Meal> getMealsList();
}
