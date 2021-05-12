package it.unicam.cs.gp.inmytable.notification;

import it.unicam.cs.gp.inmytable.allmeals.meals.Food;
import it.unicam.cs.gp.inmytable.user.IUser;
import it.unicam.cs.gp.inmytable.user.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class SubscriptionNotification<T extends IUser, F extends Food> implements INotification<T>{
    private String message;
    private T from;
    private T to;
    private LocalDate date;
    private LocalTime time;
    private ISubscription<T,F> subscription;

    public SubscriptionNotification(T from, T to, ISubscription<T,F> subscription, String message){
        this.from=from;
        this.to=to;
        this.date=LocalDate.now();
        this.time=LocalTime.now();
        this.subscription=subscription;
        this.message=message;
    }

    @Override
    public T from(){
        return from;
    }

    @Override
    public T to(){
        return to;
    }

    @Override
    public LocalDate getDate(){
        return this.date;
    }

    @Override
    public LocalTime getTime(){
        return this.time;
    }


    @Override
    public String getMsg() {
        return this.message;
    }

   // @Override
    public ISubscription<T, F> getSubscription() {
        return subscription;
    }
}
