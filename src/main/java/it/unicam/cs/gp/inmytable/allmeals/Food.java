package it.unicam.cs.gp.inmytable.allmeals;

import it.unicam.cs.gp.inmytable.user.IUser;

import java.time.LocalDate;
import java.time.LocalTime;

public interface Food {

    /**
     * return the home owner
     * @return home owner
     */
    IUser getHomeOwner();

    /**
     * used to set a date
     * @param date
     */
    void setDate(LocalDate date);

    /**
     * return the date
     * @return the date
     */
    LocalDate getDate();

    /**
     * used to set a time
     * @param time the time
     */
    void setTime(LocalTime time);

    /**
     * return the time
     * @return the time
     */
    LocalTime getTime();

    /**
     * used to set expiry date
     * @param expiryDate expiry date
     */
    void setExpiryDate(LocalDate expiryDate);

    /**
     * return expiry date
     * @return expiry date
     */
    LocalDate getExpiryDate();

    /**
     * used to set expiry time
     * @param expiryTime expiry time
     */
    void setExpiryTime(LocalTime expiryTime);

    /**
     * return expiry time
     * @return expiry time
     */
    LocalTime getExpiryTime();

    /**
     * uset to set the description
     * @param description description
     */
    void setDescription(String description);

    /**
     * return the description
     * @return description
     */
    String getDescription();

    /**
     * return the place
     * @return place
     */
    String getPlace();

    /**
     * return the price
     * @return price
     */
    String getPrice();

    /**
     * used to set a state
     * @param state MealStates
     */
    void setState(MealStates state);

    /**
     * return the state
     * @return MealStates
     */
    MealStates getState();

    /**
     * return the payment type
     * @return PaymentType
     */
    PaymentType getPaymentType();

    /**
     * return meal type
     * @return meal type
     */
    String getMealType();

    /**
     * used to set id
     * @param id the id
     */
    void setId(String id);

    /**
     * return the id
     * @return the id
     */
    String getId();

    /**
     * return consummation type
     * @return ConsummationType
     */
    ConsummationType getConsummationType();

}
