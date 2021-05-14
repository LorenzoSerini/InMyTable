package it.unicam.cs.gp.inmytable.notification;

import it.unicam.cs.gp.inmytable.user.IUser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class SimpleNotification<T extends IUser> implements INotification<T>{
    private String message;
    private T from;
    private T to;
    private LocalDate date;
    private LocalTime time;
    private String id;


    public SimpleNotification(T from, T to, String message){
        this.from=from;
        this.to=to;
        this.date=LocalDate.now();
        this.time=truncatesTime(LocalTime.now());
        this.message=message;
        this.id = UUID.randomUUID().toString();
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

    @Override
    public void setId(String id){this.id=id;}

    @Override
    public String getId(){return this.id;}

    private LocalTime truncatesTime(LocalTime time){
        String tTime = time.toString().substring(0,8);
        return LocalTime.parse(tTime);
    }

}