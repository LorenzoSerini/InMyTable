package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.allmeals.MealStates;
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


    /**
     * set log in user
     * @param logUser the log in user
     * @throws Exception
     */
    public void setLogUser(User logUser) throws Exception {
        mealsController = new MealsController(logUser);
        mealsRequestController = new MealRequestsController(logUser);
    }

    /**
     * returns home wall
     * @return home wall
     */
    public HomeWall getHomewall(){
        return HomeWall.getInstance();
    }

    /**
     * returns pending meals catalog
     * @return pending meals catalog
     */
    public List<Meal> getPendingMealCatalog(){
        return  HomeWall.getInstance().getMealCatalog().stream()
                .filter(p->p.getState().equals(MealStates.PENDING))
                .collect(Collectors.toList());
    }

    /**
     * returns pending meals request catalog
     * @return pending meals request catalog
     */
    public List<MealRequest> getPendingMealRequestCatalog(){
        return  HomeWall.getInstance().getMealRequestCatalog().stream()
                .filter(p->p.getState().equals(MealStates.PENDING))
                .collect(Collectors.toList());
    }


    /**
     * return a meal
     * @param id meal id
     * @return a meal
     */
    public Meal getAMeal(String id){
        for(Meal meal:getPendingMealCatalog()){
            if(meal.getId().equals(id)) return meal;
        }
        return null;
    }

    /**
     * return meal request
     * @param id meal request id
     * @return meal request
     */
    public MealRequest getAMealRequest(String id){
        for(MealRequest mealRequest:getPendingMealRequestCatalog()){
            if(mealRequest.getId().equals(id)) return mealRequest;
        }
        return null;
    }


    /**
     * returns meals array
     * @param mealList meal list
     * @return meals array
     */
    public String[] getMealsArray(List<Meal> mealList){
        String [] mealArray = new String[mealList.size()];
        for(int a= 0; a<mealList.size(); a++) {
            mealArray[a] = mealList.get(a).getCity()+", "+mealList.get(a).getPlace();
        }
        return mealArray;
    }

    /**
     * returns meal requests array
     * @param mealRequestsList meal requests list
     * @return meal requests array
     */
    public String[] getMealRequestsArray(List<MealRequest> mealRequestsList){
        String [] mealRequestArray = new String[mealRequestsList.size()];
        for(int a= 0; a<mealRequestsList.size(); a++) {
            mealRequestArray[a] =  mealRequestsList.get(a).getPlace();
        }
        return mealRequestArray;
    }


    /**
     * returns meals description array
     * @param mealList meals list
     * @return meals description array
     */
    public String[] getMealsDescriptionArray(List<Meal> mealList){
        String [] mealArray = new String[mealList.size()];
        for(int a= 0; a<mealList.size(); a++) {
            mealArray[a] = mealList.get(a).getDescription();
        }
        return mealArray;
    }

    /**
     * returns meals date time array
     * @param mealList meals list
     * @return meals date time array
     */
    public String[] getMealsDateTimeArray(List<Meal> mealList){
        String [] mealArray = new String[mealList.size()];
        for(int a= 0; a<mealList.size(); a++) {
            mealArray[a] = mealList.get(a).getDate().toString()+" "+mealList.get(a).getTime().toString();
        }
        return mealArray;
    }


    /**
     * returns meal requests description list
     * @param mealRequestList meal requests list
     * @return meal requests description list
     */
    public String[] getMealRequestsDescriptionArray(List<MealRequest> mealRequestList){
        String [] mealArray = new String[mealRequestList.size()];
        for(int a= 0; a<mealRequestList.size(); a++) {
            mealArray[a] = mealRequestList.get(a).getDescription();
        }
        return mealArray;
    }

    /**
     * returns meal requests date time array
     * @param mealRequestList meal requests list
     * @return meal requests date time array
     */
    public String[] getMealRequestsDateTimeArray(List<MealRequest> mealRequestList){
        String [] mealArray = new String[mealRequestList.size()];
        for(int a= 0; a<mealRequestList.size(); a++) {
            mealArray[a] = mealRequestList.get(a).getDate().toString()+" "+mealRequestList.get(a).getTime().toString();
        }
        return mealArray;
    }

    /**
     * returns meal id array
     * @param mealList meal list
     * @return meal id array
     */
    public String[] getMealsIdArray(List<Meal> mealList){
        String [] mealArray = new String[mealList.size()];
        for(int a= 0; a<mealList.size(); a++) {
            mealArray[a] = mealList.get(a).getId();
        }
        return mealArray;
    }

    /**
     * returns meal requests id array
     * @param mealRequestList meal requests list
     * @return meal requests id array
     */
    public String[] getMealRequestsIdArray(List<MealRequest> mealRequestList){
        String [] mealArray = new String[mealRequestList.size()];
        for(int a= 0; a<mealRequestList.size(); a++) {
            mealArray[a] = mealRequestList.get(a).getId();
        }
        return mealArray;
    }

}
