package it.unicam.cs.gp.inmytable.notification;

import it.unicam.cs.gp.inmytable.allmeals.Food;
import it.unicam.cs.gp.inmytable.user.IUser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class SubscriptionNotification<T extends IUser, F extends Food> implements INotification<T>{
    private String message;
    private T from;
    private T to;
    private LocalDate date;
    private LocalTime time;
    private ISubscription<T,F> subscription;
    private String id;

    /**
     * Build a Notification with a Subscription
     * @param from  user who send the notification
     * @param to    user who receive the notification
     * @param subscription  subscription you want to notify
     * @param message   message of the notification
     */
    public SubscriptionNotification(T from, T to, ISubscription<T,F> subscription, String message){
        this.date=LocalDate.now();
        this.from=from;
        this.to=to;
        this.time=truncatesTime(LocalTime.now());
        this.message=message;
        this.id = UUID.randomUUID().toString();
        this.subscription=subscription;
    }


    @Override
    public T from(){
        return from;
    }

    public void setFrom(T from){this.from=from;}

    @Override
    public T to(){
        return to;
    }

    public void setTo(T to){this.to=to;}

    @Override
    public LocalDate getDate(){
        return this.date;
    }

    public void setDate(LocalDate date){this.date=date;}

    @Override
    public LocalTime getTime(){
        return this.time;
    }

    public void setTime(LocalTime time){this.time=time;}

    @Override
    public String getMsg() {
        return this.message;
    }


    @Override
    public void setId(String id){this.id=id;}

    @Override
    public String getId(){return this.id;}

    private LocalTime truncatesTime(LocalTime time){
        String tTime = time.toString().substring(0,8);
        return LocalTime.parse(tTime);
    }

    public void setSubscription(ISubscription<T,F> subscription){this.subscription=subscription;}

    public ISubscription<T, F> getSubscription() {
        return subscription;
    }
}
