package it.unicam.cs.gp.inmytable.controllers;

import it.unicam.cs.gp.inmytable.allmeals.MealManager;
import it.unicam.cs.gp.inmytable.allmeals.meals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.allmeals.meals.MealStates;
import it.unicam.cs.gp.inmytable.allmeals.meals.PaymentType;
import it.unicam.cs.gp.inmytable.homewalls.HomeWall;
import it.unicam.cs.gp.inmytable.persistence.MealPersistence;
import it.unicam.cs.gp.inmytable.persistence.UserDB;
import it.unicam.cs.gp.inmytable.user.User;

import java.time.LocalDate;
import java.time.LocalTime;

public class MealsController {
    private User homeOwner;
    private MealManager mealManager;
    private MealPersistence mealPersistence;

    public MealsController(User logUser, MealPersistence mealPersistence) throws Exception {
        mealManager = MealManager.getInstance();
        this.homeOwner = logUser;
        this.mealPersistence=mealPersistence;
        if(HomeWall.getInstance().getMealCatalog().isEmpty()) HomeWall.getInstance().getMealCatalog().addAll(mealPersistence.getMealsList());
      //  System.out.println(HomeWall.getInstance().getMealCatalog().size());
       // System.out.println(mealPersistence.getMealsList().size());
    }

    public MealsController(User logUser) throws Exception {
        this(logUser, new UserDB());
    }

    public void cook(String date, String time, String expiryDate, String expiryTime, int maxNumUsers, String mealType, boolean freeSubscription, String place, ConsumationType consumationType, String description, String ingredients, PaymentType paymentType, String price) throws Exception {
        if (LocalDate.now().isAfter(LocalDate.parse(date)) || (LocalDate.now().isEqual(LocalDate.parse(date)) && LocalTime.now().isAfter(LocalTime.parse(time)) )) throw new IllegalArgumentException("You cannot travel in time");
        mealPersistence.registerMeal(
                mealManager.createMeal(homeOwner, maxNumUsers, LocalDate.parse(date), LocalTime.parse(time), LocalDate.parse(expiryDate), LocalTime.parse(expiryTime), mealType, freeSubscription, place, consumationType, description, ingredients, paymentType, price )
                );
    }

    public void mealRequest(String description, String mealType, ConsumationType consumationType, PaymentType payment, String date, String time, String expiryDate, String expiryTime, String price, String place, String allergy, int mealsNumber) throws Exception {
        mealManager.createMealRequest(homeOwner, mealType, consumationType, payment, description, LocalDate.parse(date), LocalTime.parse(time), LocalDate.parse(expiryDate), LocalTime.parse(expiryTime), price, place, allergy, mealsNumber);
    }


}