package it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest;

import it.unicam.cs.gp.inmytable.allmeals.meals.MealStates;
import it.unicam.cs.gp.inmytable.user.User;

import java.util.Date;

public class PublicMealRequest extends MealRequest {

    public PublicMealRequest(User host, Date date, Date expiringDate, String place) {
        super(host, date, expiringDate, place);
    }

    public void confirmRequest(User homeOwner) {
        this.setHomeOwner(homeOwner);
        this.setState(MealStates.FULL);
    }

}

