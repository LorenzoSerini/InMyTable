package it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest;

import it.unicam.cs.gp.inmytable.user.User;

public class PublicMealRequest extends MealRequest {
    @Override
    public User getHost() {
        return null;
    }

    @Override
    public void setHost() {

    }

    @Override
    public User getHomeOwner() {
        return null;
    }
}