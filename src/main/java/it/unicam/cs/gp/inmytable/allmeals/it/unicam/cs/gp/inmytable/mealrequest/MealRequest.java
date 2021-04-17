package it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest;

import it.unicam.cs.gp.inmytable.allmeals.meals.MealStates;
import it.unicam.cs.gp.inmytable.user.*;

import java.util.Date;

/**
 * MealRequest from an host to one homeOwner or more.
 */
public abstract class MealRequest implements IMealRequest {

    private Date date;
    private Date expiryDate;
    private String place;
    private User host;
    private User homeOwner;
    private MealStates state;

    /**
     * MealRequest constructor
     * @param host host who create the MealRequest
     * @param date meal's date
     * @param expiryDate meal's expiring date
     * @param place where host wants to eat
     */
    public MealRequest(User host, Date date, Date expiryDate, String place){
        this.host = host;
        this.date = date;
        this.expiryDate = expiryDate;
        this.place = place;
        this.state = MealStates.PENDING;
    }


    @Override
    public User getHost() {
        return host;
    }



    @Override
    public User getHomeOwner() {
        return homeOwner;
    }

    @Override
    public void setHost(User host) {
        this.host = host;

    }
    @Override
    public void setDate(Date date){
        this.date = date;
    }

    @Override
    public void setExpiringDate(Date expiringDate){
        this.expiryDate = expiringDate;
    }

    @Override
    public void setHomeOwner(User homeOwner){
        this.homeOwner = homeOwner;

    }

    public void setState(MealStates state){
        this.state = state;
    }
}