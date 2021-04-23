package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.allmeals.meals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.PaymentType;
import it.unicam.cs.gp.inmytable.controllers.MealsController;
import org.springframework.stereotype.Service;

@Service
public class CookService {
    private MealsController mealsController;

    public CookService(){
        mealsController=new MealsController();
    }

    public void postAMeal(String description, String mealType, String ingredients, String homeHownerAddress, String homeHownerEmail, String homeHownerNumber, String consumationType, String paymentType,  String price, String date, String expireDate, int maxNumUsers, boolean freeSubscription) throws Exception {
       //TODO: NUMERO MASSIMO USERS e FreeSubscription ANDRANNO AGGIUNTI ALL'INTERFACCIA
        String startD = date.substring(0, 10);
        String startT = date.substring(11,16);
        String expireD = expireDate.substring(0, 10);
        String expireT = expireDate.substring(11,16);
        mealsController.cook(startD, startT,expireD, expireT, maxNumUsers, mealType, freeSubscription, homeHownerAddress, ConsumationType.valueOf(consumationType.toUpperCase()), description, ingredients, PaymentType.valueOf(paymentType.toUpperCase()), price);
    }

}
