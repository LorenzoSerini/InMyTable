package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.allmeals.mealrequest.IMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.notification.SubscriptionNotification;
import it.unicam.cs.gp.inmytable.user.IUser;

import java.sql.SQLException;
import java.util.List;

public interface MealRequestPersistence extends Persistence {


    /**
     * Register a Public Meal Request
     * @param mealRequest private meal request
     * @throws SQLException
     */
    void registerPublicMealRequest(MealRequest mealRequest) throws Exception;



    void registerPrivateMealRequest(MealRequest mealRequest, SubscriptionNotification<?,?> notification) throws Exception;

    /**
     * Register a homeOwner to a meal
     * @param homeOwner The homeOwner
     * @param mealRequest The meal
     */
    void registerHomeOwnerToMealRequest(IUser homeOwner, IMealRequest mealRequest, SubscriptionNotification<?,?> notification) throws Exception;



    List<MealRequest> getMealsRequestList() throws Exception;

    /**
     * Get public meals request
     * @return public meals request
     */
    List<MealRequest> getPublicMealsRequestList() throws Exception;


    /**
     * Get private meals request
     * @return private meals request
     */
    List<MealRequest> getPrivateMealsRequestList() throws Exception;




}
