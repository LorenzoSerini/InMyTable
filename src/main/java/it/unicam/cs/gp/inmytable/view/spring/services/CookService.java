package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.allmeals.ConsummationType;
import it.unicam.cs.gp.inmytable.allmeals.PaymentType;
import it.unicam.cs.gp.inmytable.controllers.MealsController;
import it.unicam.cs.gp.inmytable.user.User;
import org.springframework.stereotype.Service;


@Service
public class CookService {
    private MealsController mealsController;


    /**
     * set log user
     * @param logUser log user
     * @throws Exception
     */
    public void setLogUser(User logUser) throws Exception {
        mealsController=new MealsController(logUser);
    }

    /**
     * used to post a meal
     * @param description meal description
     * @param mealType meal type
     * @param ingredients meal ingredients
     * @param city meal city
     * @param address meal address
     * @param consummationType meal consummation type
     * @param paymentType meal payment type
     * @param price meal price
     * @param date meal date
     * @param expiryDate meal expiry date
     * @param maxNumUsers meal max number user
     * @param freeSubscription meal is free subscription?
     * @throws Exception
     */
    public void postAMeal(String description, String mealType, String ingredients, String city, String address, String consummationType, String paymentType,  String price, String date, String expiryDate, int maxNumUsers, boolean freeSubscription) throws Exception {
        String startD = date.substring(0, 10);
        String startT = date.substring(11,16);
        String expireD = expiryDate.substring(0, 10);
        String expireT = expiryDate.substring(11,16);
        mealsController.cook(startD, startT,expireD, expireT, maxNumUsers, mealType, freeSubscription, city, address, ConsummationType.valueOf(consummationType.toUpperCase()), description, ingredients, PaymentType.valueOf(paymentType.toUpperCase()), price);
    }

}
