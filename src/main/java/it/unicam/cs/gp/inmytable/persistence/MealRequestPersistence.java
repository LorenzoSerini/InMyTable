package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.notification.SubscriptionNotification;
import it.unicam.cs.gp.inmytable.user.User;

import java.sql.SQLException;
import java.util.List;

public interface MealRequestPersistence extends Persistence {


    /**
     * Register a Private Meal Request
     * @param mealRequest private meal request
     * @throws SQLException
     */
    void registerMealRequest(MealRequest mealRequest) throws Exception;

    /**
     * Register a homeOwner to a meal
     * @param homeOwner The homeOwner
     * @param mealRequest The meal
     */
    void registerHomeOwnerToMealRequest(User homeOwner, MealRequest mealRequest, SubscriptionNotification<?,?> notification) throws Exception;



    List<MealRequest> getMealsRequestMap() throws Exception;

    /**
     * Get public meals request
     * @return public meals request
     */
    List<MealRequest> getPublicMealsRequestMap() throws Exception;


    /**
     * Get private meals request
     * @return private meals request
     */
    List<MealRequest> getPrivateMealsRequestMap() throws Exception;




}
