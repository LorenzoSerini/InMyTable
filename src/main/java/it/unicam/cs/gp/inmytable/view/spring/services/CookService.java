package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.allmeals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.PaymentType;
import it.unicam.cs.gp.inmytable.controllers.MealsController;
import it.unicam.cs.gp.inmytable.user.User;
import org.springframework.stereotype.Service;


@Service
public class CookService {
    private MealsController mealsController;


    public CookService(){
    }

    public void setLogUser(User logUser) throws Exception {
        mealsController=new MealsController(logUser);
    }

    public void postAMeal(String description, String mealType, String ingredients, String city, String address, String consummationType, String paymentType,  String price, String date, String expireDate, int maxNumUsers, boolean freeSubscription) throws Exception {
        String startD = date.substring(0, 10);
        String startT = date.substring(11,16);
        String expireD = expireDate.substring(0, 10);
        String expireT = expireDate.substring(11,16);
        mealsController.cook(startD, startT,expireD, expireT, maxNumUsers, mealType, freeSubscription, city, address, ConsumationType.valueOf(consummationType.toUpperCase()), description, ingredients, PaymentType.valueOf(paymentType.toUpperCase()), price);
    }

}
