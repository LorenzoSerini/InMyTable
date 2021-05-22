package it.unicam.cs.gp.inmytable.allmeals;

import it.unicam.cs.gp.inmytable.user.IUser;

import java.time.LocalDate;
import java.time.LocalTime;

public interface Food {

    IUser getHomeOwner();

    void setDate(LocalDate date);

    LocalDate getDate();

    void setTime(LocalTime time);

    LocalTime getTime();

    void setExpiryDate(LocalDate expiryDate);

    LocalDate getExpiryDate();

    void setExpiryTime(LocalTime expiryTime);

    LocalTime getExpiryTime();

    void setDescription(String description);

    String getDescription();

    String getPlace();

    String getPrice();

    void setState(MealStates state);

    MealStates getState();

    PaymentType getPaymentType();

    String getMealType();

    void setId(String id);

    String getId();

    ConsumationType getConsummationType();

}
