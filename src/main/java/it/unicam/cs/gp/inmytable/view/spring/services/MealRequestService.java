package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.allmeals.meals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.PaymentType;
import it.unicam.cs.gp.inmytable.controllers.MealsController;
import org.springframework.stereotype.Service;


@Service
public class MealRequestService {
    private MealsController mealsController;

    public MealRequestService(){
        mealsController=new MealsController();
    }

    public void postAPublicMealRequest(String description, String mealType, String consummationType, String payment, String date, String expiryDate, String price, String place, String allergy, int mealsNumber) throws Exception {
        String startD = date.substring(0, 10);
        String startT = date.substring(11,16);
        String expiryD = expiryDate.substring(0, 10);
        String expiryT = expiryDate.substring(11,16);
        mealsController.mealRequest(description, mealType, ConsumationType.valueOf(consummationType.toUpperCase()), PaymentType.valueOf(payment.toUpperCase()), startD, startT, expiryD, expiryT, price, place, allergy, mealsNumber);
    }
}
