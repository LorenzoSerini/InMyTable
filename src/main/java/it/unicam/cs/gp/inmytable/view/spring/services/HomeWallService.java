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

   /* public Meal getAMeal(int hashCode){
        for(Meal meal:getPendingMealCatalog()){
            if(hashCode==meal.hashCode()) return meal;
        }
        return null;
    }*/

    public Meal getAMeal(String id){
        for(Meal meal:getPendingMealCatalog()){
            if(meal.getId().equals(id)) return meal;
        }
        return null;
    }

   /* public MealRequest getAMealRequest(int hashCode){
        for(MealRequest mealRequest:getPendingMealRequestCatalog()){
            if(hashCode==mealRequest.hashCode()) return mealRequest;
        }
        return null;
    }*/

    public MealRequest getAMealRequest(String id){
        for(MealRequest mealRequest:getPendingMealRequestCatalog()){
            if(mealRequest.getId().equals(id)) return mealRequest;
        }
        return null;
    }





/*
    public Meal[] getMealsArray(List<Meal> mealList){
        Meal [] mealArray = new Meal[mealList.size()];
        for(int a= 0; a<mealList.size(); a++) {
            mealArray[a] = mealList.get(a);
        }
        return mealArray;
    }


    public MealRequest[] getMealRequestsArray(List<MealRequest> mealRequestsList){
        MealRequest [] mealRequestArray = new MealRequest[mealRequestsList.size()];
        for(int a= 0; a<mealRequestsList.size(); a++) {
            mealRequestArray[a] =  mealRequestsList.get(a);
        }
        return mealRequestArray;
    }

 */

    public String[] getMealsArray(List<Meal> mealList){
        String [] mealArray = new String[mealList.size()];
        for(int a= 0; a<mealList.size(); a++) {
            mealArray[a] = mealList.get(a).getCity()+", "+mealList.get(a).getPlace();
        }
        return mealArray;
    }


    public String[] getMealRequestsArray(List<MealRequest> mealRequestsList){
        String [] mealRequestArray = new String[mealRequestsList.size()];
        for(int a= 0; a<mealRequestsList.size(); a++) {
            mealRequestArray[a] =  mealRequestsList.get(a).getPlace();
        }
        return mealRequestArray;
    }



    public String[] getMealsDescriptionArray(List<Meal> mealList){
        String [] mealArray = new String[mealList.size()];
        for(int a= 0; a<mealList.size(); a++) {
            mealArray[a] = mealList.get(a).getDescription();
        }
        return mealArray;
    }


    public String[] getMealsDateTimeArray(List<Meal> mealList){
        String [] mealArray = new String[mealList.size()];
        for(int a= 0; a<mealList.size(); a++) {
            mealArray[a] = mealList.get(a).getDate().toString()+" "+mealList.get(a).getTime().toString();
        }
        return mealArray;
    }



    public String[] getMealRequestsDescriptionArray(List<MealRequest> mealRequestList){
        String [] mealArray = new String[mealRequestList.size()];
        for(int a= 0; a<mealRequestList.size(); a++) {
            mealArray[a] = mealRequestList.get(a).getDescription();
        }
        return mealArray;
    }


    public String[] getMealRequestsDateTimeArray(List<MealRequest> mealRequestList){
        String [] mealArray = new String[mealRequestList.size()];
        for(int a= 0; a<mealRequestList.size(); a++) {
            mealArray[a] = mealRequestList.get(a).getDate().toString()+" "+mealRequestList.get(a).getTime().toString();
        }
        return mealArray;
    }

    public String[] getMealsIdArray(List<Meal> mealList){
        String [] mealArray = new String[mealList.size()];
        for(int a= 0; a<mealList.size(); a++) {
            mealArray[a] = mealList.get(a).getId();
        }
        return mealArray;
    }


    public String[] getMealRequestsIdArray(List<MealRequest> mealRequestList){
        String [] mealArray = new String[mealRequestList.size()];
        for(int a= 0; a<mealRequestList.size(); a++) {
            mealArray[a] = mealRequestList.get(a).getId();
        }
        return mealArray;
    }

}
