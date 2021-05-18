package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.user.User;

import java.util.Map;

public interface Persistence {

    /**
     *A map with the username associated with each user
     * @return the username associated with each user
     * @throws Exception
     */
    Map<String, User> getUsers() throws Exception;

    Map<String, Meal> getMealsMap();

    Map<String, MealRequest> getMealsRequestMap();
}
