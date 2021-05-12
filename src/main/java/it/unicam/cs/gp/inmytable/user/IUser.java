package it.unicam.cs.gp.inmytable.user;

import it.unicam.cs.gp.inmytable.allmeals.mealrequest.IMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
import it.unicam.cs.gp.inmytable.notification.SimpleNotification;
import it.unicam.cs.gp.inmytable.notification.SubscriptionNotification;

import java.time.LocalDate;
import java.util.List;

public interface IUser {

    void setUsername(String username);

    String getUsername();

    void setEmail(String email);

    String getEmail();

    void setTelephoneNumber(String telephoneNumber);

    String getTelephoneNumber();

    String getFirstName();

    String getLastName();

    void setPassword(int password);

    int getPassword();

    LocalDate getBirth();

    String getFiscalCode();

    void setId(String id);

    String getId();

    void setCity(String city);

    String getCity();

    void setAddress(String address);

    String getAddress();

    void setAvailableToRequests(boolean bool);

    boolean getAvailableToRequests();

    FeedbackBox getFeedbackBox();

    List<SimpleNotification<IUser>> getSimpleNotifications();

    List<SubscriptionNotification<IUser, IMeal>> getMealNotifications();

    List<SubscriptionNotification<IUser, IMealRequest>> getMealRequestNotifications();

}
