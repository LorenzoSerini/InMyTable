package it.unicam.cs.gp.inmytable.notification;


import it.unicam.cs.gp.inmytable.user.IUser;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Notification
 * @param <T>  class that extends IUser
 */
public interface INotification<T extends IUser>{

    /**
     * Who send the Notification
     * @return  T
     */
    T from();

    /**
     *
     * @return
     */
    T to();

    LocalDate getDate();

    LocalTime getTime();

    String getMsg();

    void setId(String id);

    String getId();

}
