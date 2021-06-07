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

    public void setLogUser(User logUser) throws Exception {
        this.mealsController = new MealsController(logUser);
    }

    public String getFreeSubscription(IMeal meal){
        if(meal.isFreeSubscription()) return "Libera";
        else return "Su accettazione del padrone di casa";
    }

    public String getConsummationType(Food meal){
        switch (meal.getConsummationType()){
            case AT_HOME: return "In casa di colui che lo ha pubblicato";
            case TAKEAWAY: return "Takeaway";
            case BOTH: return "Sia in casa di colui che ha pubblicato l'annuncio che Takeaway";
        }
        return null;
    }

    public String getPayment(Food meal){
        switch (meal.getPaymentType()){
            case CASH: return "In Denaro";
            case EXCHANGE: return "Baratto";
            case FREE: return "Gratis";
        }
        return null;
    }


    public boolean canISignUp(User logUser, Meal meal){
        return !meal.getHomeOwner().equals(logUser) && !meal.getUserList().contains(logUser) && !isMyRequestPending(meal);
    }

    public void joinToMeal(Meal meal) throws Exception {
        this.mealsController.joinToMeal(meal);
    }

    private boolean isMyRequestPending(Meal meal){
        for(SubscriptionNotification<IUser, IMeal> m:meal.getHomeOwner().getMealNotifications()){
            if(m.getSubscription().getFood().equals(meal)&&m.getSubscription().getState().equals(SubscriptionStates.PENDING)) return true;
        }
        return false;
    }

}
