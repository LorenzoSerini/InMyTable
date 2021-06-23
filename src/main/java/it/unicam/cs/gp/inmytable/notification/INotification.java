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
     * Who received the notification
     * @return
     */
    T to();

    /**
     * returns notification date
     * @return date notification
     */
    LocalDate getDate();

    /**
     * used to set notification date
     * @param date notification date
     */
    void setDate(LocalDate date);

    /**
     * returns notification time
     * @return notification time
     */
    LocalTime getTime();

    /**
     * used to set notification time
     * @param time notification time
     */
    void setTime(LocalTime time);

    /**
     * return notification message
     * @return
     */
    String getMsg();

    /**
     * used to set notification id
     * @param id notification id
     */
    void setId(String id);

    /**
     * returns notification id
     * @return notification id
     */
    String getId();



}
