package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.allmeals.meals.MealStates;
import it.unicam.cs.gp.inmytable.controllers.MealsController;
import it.unicam.cs.gp.inmytable.controllers.UserController;
import it.unicam.cs.gp.inmytable.user.User;
import org.springframework.stereotype.Service;

@Service
public class MealSubscriptionService {
    private MealsController mealsController;

    public void setLogUser(User logUser) throws Exception {
        this.mealsController = new MealsController(logUser);
    }

    public String getFreeSubscription(Meal meal){
        if(meal.isFreeSubscription()) return "Libera";
        else return "Su accettazione del padrone di casa";
    }

    public String getConsummationType(Meal meal){
        switch (meal.getConsummationType()){
            case AT_HOME: return "In casa di colui che lo ha pubblicato";
            case TAKEAWAY: return "Takeaway";
            case BOTH: return "Sia in casa di colui che ha pubblicato l'annuncio che Takeaway";
        }
        return null;
    }

    public boolean canISignUp(User logUser, Meal meal){
        return !meal.getHomeOwner().equals(logUser) && !meal.getUserList().contains(logUser);
    }

    public void joinToMeal(Meal meal) throws Exception {
        this.mealsController.joinToMeal(meal);
    }

}
