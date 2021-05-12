package it.unicam.cs.gp.inmytable.notification;

import it.unicam.cs.gp.inmytable.user.IUser;

import java.time.LocalDate;
import java.time.LocalTime;

public class SimpleNotification<T extends IUser> implements INotification<T>{
    private String message;
    private T from;
    private T to;
    private LocalDate date;
    private LocalTime time;

    public SimpleNotification(T from, T to, String message){
        this.from=from;
        this.to=to;
        this.date=LocalDate.now();
        this.time=LocalTime.now();
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

}