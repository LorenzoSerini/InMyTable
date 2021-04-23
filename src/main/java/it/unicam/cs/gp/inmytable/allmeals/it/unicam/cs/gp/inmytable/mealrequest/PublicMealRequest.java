package it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest;

import it.unicam.cs.gp.inmytable.allmeals.meals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.MealStates;
import it.unicam.cs.gp.inmytable.allmeals.meals.PaymentType;
import it.unicam.cs.gp.inmytable.user.User;

import java.time.LocalDate;
import java.time.LocalTime;

public class PublicMealRequest extends MealRequest {

    public PublicMealRequest(User host, String mealType, ConsumationType consumationType, PaymentType payment, String description, LocalDate date, LocalTime time, LocalDate expiryDate, LocalTime expiryTime, String price, String place, String allergy, int mealsNumber) {
        super(host, mealType, consumationType, payment, description, date, time, expiryDate, expiryTime, price, place, allergy, mealsNumber);
    }

    public void confirmRequest(User homeOwner) {
        this.setHomeOwner(homeOwner);
        this.setState(MealStates.FULL);
    }

}

