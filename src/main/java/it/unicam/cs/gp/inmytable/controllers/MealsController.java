package it.unicam.cs.gp.inmytable.controllers;

import it.unicam.cs.gp.inmytable.allmeals.MealManager;
import it.unicam.cs.gp.inmytable.allmeals.meals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.PaymentType;
import it.unicam.cs.gp.inmytable.user.User;

import java.time.LocalDate;
import java.time.LocalTime;

public class MealsController {
    //_______________________________________________________________________________________________________________________________________________________
    //TODO NB: Al metodo MealManager.createMeal dovrà essere passato automaticamente l'homeowner (sarà l'utente al momento loggato) preso dal costruttore della classe !!
    User defaultUser = new User("Johnny76", "john@example.com", "000 000000", "John", "Doe", "example", LocalDate.parse("1950-01-01"));
    //_______________________________________________________________________________________________________________________________________________________
    private MealManager mealManager;

    public MealsController(){
        mealManager = new MealManager();
    }

    public void cook(String date, String time, String expiryDate, String expiryTime, int maxNumUsers, String mealType, boolean freeSubscription, String place, ConsumationType consumationType, String description, String ingredients, PaymentType paymentType, String price) throws Exception {
        mealManager.createMeal(defaultUser, maxNumUsers, LocalDate.parse(date), LocalTime.parse(time), LocalDate.parse(expiryDate), LocalTime.parse(expiryTime), mealType, freeSubscription, place, consumationType, description, ingredients, paymentType, price );
    }

    public void mealRequest(String description, String mealType, ConsumationType consumationType, PaymentType payment, String date, String time, String expiryDate, String expiryTime, String price, String place, String allergy, int mealsNumber) throws Exception {
        mealManager.createMealRequest(defaultUser, mealType, consumationType, payment, description, LocalDate.parse(date), LocalTime.parse(time), LocalDate.parse(expiryDate), LocalTime.parse(expiryTime), price, place, allergy, mealsNumber);
    }


}