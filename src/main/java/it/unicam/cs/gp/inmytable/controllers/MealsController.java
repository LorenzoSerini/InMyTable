package it.unicam.cs.gp.inmytable.controllers;

import it.unicam.cs.gp.inmytable.allmeals.MealManager;
import it.unicam.cs.gp.inmytable.allmeals.meals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.PaymentType;
import it.unicam.cs.gp.inmytable.user.User;

import java.time.LocalDate;
import java.time.LocalTime;

public class MealsController {
    private User homeOwner;
    private MealManager mealManager;

    public MealsController(User logUser){
        mealManager = new MealManager();
        this.homeOwner = logUser;
    }

    public void cook(String date, String time, String expiryDate, String expiryTime, int maxNumUsers, String mealType, boolean freeSubscription, String place, ConsumationType consumationType, String description, String ingredients, PaymentType paymentType, String price) throws Exception {
        mealManager.createMeal(homeOwner, maxNumUsers, LocalDate.parse(date), LocalTime.parse(time), LocalDate.parse(expiryDate), LocalTime.parse(expiryTime), mealType, freeSubscription, place, consumationType, description, ingredients, paymentType, price );
    }

    public void mealRequest(String description, String mealType, ConsumationType consumationType, PaymentType payment, String date, String time, String expiryDate, String expiryTime, String price, String place, String allergy, int mealsNumber) throws Exception {
        mealManager.createMealRequest(homeOwner, mealType, consumationType, payment, description, LocalDate.parse(date), LocalTime.parse(time), LocalDate.parse(expiryDate), LocalTime.parse(expiryTime), price, place, allergy, mealsNumber);
    }


}