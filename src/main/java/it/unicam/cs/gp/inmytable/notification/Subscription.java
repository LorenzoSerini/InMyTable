package it.unicam.cs.gp.inmytable.notification;

import it.unicam.cs.gp.inmytable.allmeals.Food;
import it.unicam.cs.gp.inmytable.user.IUser;


public abstract class Subscription<T extends IUser, F extends Food> implements ISubscription<T,F>{//, Notification {

    private SubscriptionStates state;
    private T user;
    private F food;

    public Subscription(T user, F food){
        this.user=user;
        this.food=food;
        this.state=SubscriptionStates.PENDING;
    }

    @Override
    public T getUser(){return this.user;}

    @Override
    public F getFood(){return this.food;}

    @Override
    public SubscriptionStates getState(){
        return this.state;
    }

    @Override
    public void setState(SubscriptionStates state){this.state=state;}


}
