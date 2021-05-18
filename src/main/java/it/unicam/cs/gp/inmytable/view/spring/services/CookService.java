package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.allmeals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.PaymentType;
import it.unicam.cs.gp.inmytable.controllers.MealsController;
import it.unicam.cs.gp.inmytable.user.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CookService {
    private MealsController mealsController;


    public CookService(){
    }

    public void setLogUser(User logUser) throws Exception {
        mealsController=new MealsController(logUser);
    }

    public void postAMeal(String description, String mealType, String ingredients, String homeOwnerAddress, String consumationType, String paymentType,  String price, String date, String expireDate, int maxNumUsers, boolean freeSubscription) throws Exception {
        String startD = date.substring(0, 10);
        String startT = date.substring(11,16);
        String expireD = expireDate.substring(0, 10);
        String expireT = expireDate.substring(11,16);
        mealsController.cook(startD, startT,expireD, expireT, maxNumUsers, mealType, freeSubscription, homeOwnerAddress, ConsumationType.valueOf(consumationType.toUpperCase()), description, ingredients, PaymentType.valueOf(paymentType.toUpperCase()), price);
    }

}
