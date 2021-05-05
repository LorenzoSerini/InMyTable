package it.unicam.cs.gp.inmytable.controllers;

import it.unicam.cs.gp.inmytable.allmeals.MealManager;
import it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest.PublicMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.meals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.allmeals.meals.MealStates;
import it.unicam.cs.gp.inmytable.allmeals.meals.PaymentType;
import it.unicam.cs.gp.inmytable.homewalls.HomeWall;
import it.unicam.cs.gp.inmytable.persistence.MealDB;
import it.unicam.cs.gp.inmytable.persistence.MealPersistence;
import it.unicam.cs.gp.inmytable.persistence.MealRequestDB;
import it.unicam.cs.gp.inmytable.persistence.MealRequestPersistence;
import it.unicam.cs.gp.inmytable.user.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Collectors;

public class MealsController {
    private User logUser;
    private MealManager mealManager;
    private MealPersistence mealPersistence;
    private MealRequestPersistence mealRequestPersistence;

    public MealsController(User logUser, MealPersistence mealPersistence, MealRequestPersistence mealRequestPersistence) throws Exception {
        mealManager = MealManager.getInstance();
        this.logUser = logUser;
        this.mealPersistence=mealPersistence;
        this.mealRequestPersistence=mealRequestPersistence;
        if(HomeWall.getInstance().getMealCatalog().isEmpty()) HomeWall.getInstance().getMealCatalog().addAll(mealPersistence.getMealsList());
        if(HomeWall.getInstance().getMealRequestCatalog().isEmpty()) HomeWall.getInstance().getMealRequestCatalog().addAll(mealRequestPersistence.getPublicMealsRequestMap());
    }

    public MealsController(User logUser) throws Exception {
        this(logUser, new MealDB(), new MealRequestDB());
    }

    public void cook(String date, String time, String expiryDate, String expiryTime, int maxNumUsers, String mealType, boolean freeSubscription, String place, ConsumationType consumationType, String description, String ingredients, PaymentType paymentType, String price) throws Exception {
        if (LocalDate.now().isAfter(LocalDate.parse(date)) || (LocalDate.now().isEqual(LocalDate.parse(date)) && LocalTime.now().isAfter(LocalTime.parse(time)) )) throw new IllegalArgumentException("You cannot travel in time");
        mealPersistence.registerMeal(
                mealManager.createMeal(logUser, maxNumUsers, LocalDate.parse(date), LocalTime.parse(time), LocalDate.parse(expiryDate), LocalTime.parse(expiryTime), mealType, freeSubscription, place, consumationType, description, ingredients, paymentType, price )
                );
    }

    public void mealRequest(String description, String mealType, ConsumationType consumationType, PaymentType payment, String date, String time, String expiryDate, String expiryTime, String price, String place, String allergy, int mealsNumber) throws Exception {
        if (LocalDate.now().isAfter(LocalDate.parse(date)) || (LocalDate.now().isEqual(LocalDate.parse(date)) && LocalTime.now().isAfter(LocalTime.parse(time))))
            throw new IllegalArgumentException("You cannot travel in time");
        PublicMealRequest publicMealRequest = mealManager.createMealRequest(logUser, mealType, consumationType, payment, description, LocalDate.parse(date), LocalTime.parse(time),
                LocalDate.parse(expiryDate), LocalTime.parse(expiryTime), price, place, allergy, mealsNumber );
        mealRequestPersistence.registerMealRequest(publicMealRequest, publicMealRequest.getNotificationState());
    }


}