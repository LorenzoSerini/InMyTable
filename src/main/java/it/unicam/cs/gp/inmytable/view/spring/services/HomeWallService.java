package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest.PublicMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.allmeals.meals.MealStates;
import it.unicam.cs.gp.inmytable.homewalls.HomeWall;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeWallService {

    public HomeWall getHomewall(){
        return HomeWall.getInstance();
    }


    public List<Meal> getPendingMealCatalog(){
        return  HomeWall.getInstance().getMealCatalog().stream()
                .filter(p->p.getState().equals(MealStates.PENDING))
                .collect(Collectors.toList());
    }

    public List<PublicMealRequest> getPendingMealRequestCatalog(){
        return  HomeWall.getInstance().getMealRequestCatalog().stream()
                .filter(p->p.getState().equals(MealStates.PENDING))
                .collect(Collectors.toList());
    }
}