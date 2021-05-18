package it.unicam.cs.gp.inmytable.notification;

import it.unicam.cs.gp.inmytable.allmeals.*;
import it.unicam.cs.gp.inmytable.user.IUser;

import java.time.LocalDate;
import java.time.LocalTime;

public interface INotification<T extends IUser>{

    T from();

    T to();

    LocalDate getDate();

    LocalTime getTime();

    String getMsg();

    void setId(String id);

    String getId();

}
