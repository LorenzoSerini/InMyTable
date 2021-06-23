package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.allmeals.Food;
import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.controllers.MealsController;
import it.unicam.cs.gp.inmytable.notification.SubscriptionNotification;
import it.unicam.cs.gp.inmytable.notification.SubscriptionStates;
import it.unicam.cs.gp.inmytable.user.IUser;
import it.unicam.cs.gp.inmytable.user.User;
import org.springframework.stereotype.Service;

@Service
public class MealSubscriptionService {
    private MealsController mealsController;

    /**
     * set log user
     * @param logUser the log user
     * @throws Exception
     */
    public void setLogUser(User logUser) throws Exception {
        this.mealsController = new MealsController(logUser);
    }

    /**
     * returns italian translate subscription string
     * @param meal meal
     * @return italian translate subscription string
     */
    public String getFreeSubscription(IMeal meal){
        if(meal.isFreeSubscription()) return "Libera";
        else return "Su accettazione del padrone di casa";
    }

    /**
     * returns italian translate consummation type string
     * @param meal the meal
     * @return italian translate consummation type string
     */
    public String getConsummationType(Food meal){
        switch (meal.getConsummationType()){
            case AT_HOME: return "In casa di colui che lo ha pubblicato";
            case TAKEAWAY: return "Takeaway";
            case BOTH: return "Sia in casa di colui che ha pubblicato l'annuncio che Takeaway";
        }
        return null;
    }

    /**
     * returns italian translate payment string
     * @param meal the meal
     * @return italian translate payment string
     */
    public String getPayment(Food meal){
        switch (meal.getPaymentType()){
            case CASH: return "In Denaro";
            case EXCHANGE: return "Baratto";
            case FREE: return "Gratis";
        }
        return null;
    }

    /**
     * if i can sign up returns true else false
     * @param logUser the log user
     * @param meal the meal
     * @return if i can sign up returns true else false
     */
    public boolean canISignUp(User logUser, Meal meal){
        return !meal.getHomeOwner().equals(logUser) && !meal.getUserList().contains(logUser) && !isMyRequestPending(meal);
    }

    /**
     * used this method to joi to meal
     * @param meal the meal
     * @throws Exception
     */
    public void joinToMeal(Meal meal) throws Exception {
        this.mealsController.joinToMeal(meal);
    }

    /**
     * if my request in pending returns true else false
     * @param meal the meal
     * @return if my request in pending returns true else false
     */
    private boolean isMyRequestPending(Meal meal){
        for(SubscriptionNotification<IUser, IMeal> m:meal.getHomeOwner().getMealNotifications()){
            if(m.getSubscription().getFood().equals(meal)&&m.getSubscription().getState().equals(SubscriptionStates.PENDING)) return true;
        }
        return false;
    }

}
