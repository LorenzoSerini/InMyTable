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

    /**
     * set log user
     * @param logUser logUser
     * @throws Exception
     */
    public void setLogUser(User logUser) throws Exception {
        this.mealsController = new MealsController(logUser);
        mealRequestsController = new MealRequestsController(logUser);
    }

    /**
     * returns a meal
     * @param id meal id
     * @return a meal
     */
    public IMeal getMeal(String id){
        return this.mealsController.getMeal(id);
    }

    /**
     * returns meal request
     * @param id meal requests id
     * @return meal request
     */
    public IMealRequest getMealRequest(String id){
        return this.mealRequestsController.getMealRequest(id);
    }

    /**
     * returns published meals
     * @return published meals
     * @throws Exception
     */
    public List<Meal> showPublishedMeals() throws Exception {
        return mealsController.showPublishedMeals();
    }

    /**
     * returns published meals with specific predicate
     * @param predicate the predicate
     * @return published meals with specific predicate
     * @throws Exception
     */
    public List<Meal> showPublishedMeals(Predicate<Meal> predicate) throws Exception {
        return mealsController.showPublishedMeals(predicate);
    }

    /**
     * returns published meal requests
     * @return published meal requests
     * @throws Exception
     */
    public List<MealRequest> showPublishedMealRequests() throws Exception {
        return mealRequestsController.showPublishedMealRequests();
    }

    /**
     * returns published meal requests with specific predicate
     * @param predicate the predicate
     * @return published meal requests with specific predicate
     * @throws Exception
     */
    public List<MealRequest> showPublishedMealRequests(Predicate<MealRequest> predicate) throws Exception {
        return mealRequestsController.showPublishedMealRequests(predicate);
    }

    /**
     * returns answered meal requests
     * @return answered meal requests
     * @throws Exception
     */
    public List<MealRequest> showAnsweredMealRequests() throws Exception {
        return mealRequestsController.showAnsweredMealRequests();
    }

    /**
     * returns answered meal requests with specific predicate
     * @param predicate the predicate
     * @return answered meal requests with specific predicate
     * @throws Exception
     */
    public List<MealRequest> showAnsweredMealRequests(Predicate<MealRequest> predicate) throws Exception {
        return mealRequestsController.showAnsweredMealRequests(predicate);
    }

    /**
     * returns closed attended meals
     * @return closed attended meals
     * @throws Exception
     */
    public List<Meal> showClosedAttendedMeals() throws Exception {
        return mealsController.showAttendedMeals(p->p.getState().equals(MealStates.EXPIRED));
    }

    /**
     * returns not closed attended meals
     * @return not closed attended meals
     * @throws Exception
     */
    public List<Meal> showNotClosedAttendedMeals() throws Exception {
        return mealsController.showAttendedMeals(p->!p.getState().equals(MealStates.EXPIRED));
    }


}
