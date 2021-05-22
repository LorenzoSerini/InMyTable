package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.allmeals.MealStates;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.IMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.controllers.MealRequestsController;
import it.unicam.cs.gp.inmytable.controllers.MealsController;
import it.unicam.cs.gp.inmytable.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class LedgerService {
    private MealsController mealsController;
    private MealRequestsController mealRequestsController;

    public void setLogUser(User logUser) throws Exception {
        this.mealsController = new MealsController(logUser);
        mealRequestsController = new MealRequestsController(logUser);
    }

    public IMeal getMeal(String id){
        return this.mealsController.getMeal(id);
    }

    public IMealRequest getMealRequest(String id){
        return this.mealRequestsController.getMealRequest(id);
    }

    public List<Meal> showPublishedMeals() throws Exception {
        return mealsController.showPublishedMeals();
    }

    public List<Meal> showPublishedMeals(Predicate<Meal> predicate) throws Exception {
        return mealsController.showPublishedMeals(predicate);
    }

    public List<MealRequest> showPublishedMealRequests() throws Exception {
        return mealRequestsController.showPublishedMealRequests();
    }

    public List<Meal> showClosedAttendedMeals() throws Exception {
        return mealsController.showAttendedMeals(p->!p.getState().equals(MealStates.PENDING));
    }

    public List<Meal> showNotClosedAttendedMeals() throws Exception {
        return mealsController.showAttendedMeals(p->p.getState().equals(MealStates.PENDING));
    }
}
