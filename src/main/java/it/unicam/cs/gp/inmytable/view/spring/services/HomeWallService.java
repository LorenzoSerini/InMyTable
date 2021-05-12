package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.allmeals.meals.MealStates;
import it.unicam.cs.gp.inmytable.controllers.MealRequestsController;
import it.unicam.cs.gp.inmytable.controllers.MealsController;
import it.unicam.cs.gp.inmytable.homewalls.HomeWall;
import it.unicam.cs.gp.inmytable.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeWallService {
    private MealsController mealsController;
    private MealRequestsController mealsRequestController;


    public void setLogUser(User logUser) throws Exception {
        mealsController = new MealsController(logUser);
        mealsRequestController = new MealRequestsController(logUser);
    }

    public HomeWall getHomewall(){
        return HomeWall.getInstance();
    }

    public List<Meal> getPendingMealCatalog(){
        return  HomeWall.getInstance().getMealCatalog().stream()
                .filter(p->p.getState().equals(MealStates.PENDING))
                .collect(Collectors.toList());
    }

    public List<MealRequest> getPendingMealRequestCatalog(){
        return  HomeWall.getInstance().getMealRequestCatalog().stream()
                .filter(p->p.getState().equals(MealStates.PENDING))
                .collect(Collectors.toList());
    }

    public Meal getAMeal(int hashCode){
        for(Meal meal:getPendingMealCatalog()){
            if(hashCode==meal.hashCode()) return meal;
        }
        return null;
    }

    public MealRequest getAMealRequest(int hashCode){
        for(MealRequest mealRequest:getPendingMealRequestCatalog()){
            if(hashCode==mealRequest.hashCode()) return mealRequest;
        }
        return null;
    }

}
