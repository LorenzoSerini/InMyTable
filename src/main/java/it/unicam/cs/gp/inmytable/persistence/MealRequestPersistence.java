package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest.PrivateMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest.PublicMealRequest;
import it.unicam.cs.gp.inmytable.notification.NotificationStates;
import it.unicam.cs.gp.inmytable.notification.Subscription;
import it.unicam.cs.gp.inmytable.user.User;

import java.sql.SQLException;
import java.util.List;

public interface MealRequestPersistence extends Persistence {
    /**
     * Register a Public Meal Request
     * @param mealRequest public meal request
     * @param notificationStates notification state
     * @throws SQLException
     */
   void registerMealRequest(PublicMealRequest mealRequest, NotificationStates notificationStates) throws SQLException;


    /**
     * Register a Private Meal Request
     * @param mealRequest private meal request
     * @param notificationStates notification state
     * @throws SQLException
     */
    void registerMealRequest(PrivateMealRequest mealRequest, NotificationStates notificationStates) throws SQLException;

    /**
     * Register a homeOwner to a meal
     * @param homeOwner The homeOwner
     * @param mealRequest The meal
     * @param subscription The meal subscription
     */
    void registerHomeOwnerToMealRequest(User homeOwner, MealRequest mealRequest, Subscription subscription) throws SQLException;


    /**
     * Get public meals request
     * @return public meals request
     */
    List<PublicMealRequest> getPublicMealsRequestMap() throws Exception;


    /**
     * Get private meals request
     * @return private meals request
     */
    List<PrivateMealRequest> getPrivateMealsRequestMap() throws Exception;

}
