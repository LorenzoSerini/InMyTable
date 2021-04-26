package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import org.springframework.stereotype.Service;

@Service
public class MealSubscriptionService {

    public String getFreeSubscription(Meal meal){
        if(meal.isFreeSubscription()) return "Libera";
        else return "Su accettazione del padrone di casa";
    }

    public String getConsummationType(Meal meal){
        switch (meal.getConsumationType()){
            case AT_HOME: return "In casa di colui che ha pubblicato l'annuncio";
            case TAKEAWAY: return "Takeaway";
            case BOTH: return "Sia in casa di colui che ha pubblicato l'annuncio che Takeaway";
        }
        return null;
    }

}
