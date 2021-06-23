package it.unicam.cs.gp.inmytable.user;

import it.unicam.cs.gp.inmytable.allmeals.mealrequest.IMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
import it.unicam.cs.gp.inmytable.notification.SimpleNotification;
import it.unicam.cs.gp.inmytable.notification.SubscriptionNotification;

import java.time.LocalDate;
import java.util.List;

public interface IUser {

    /**
     * used to set username
     * @param username username
     */
    void setUsername(String username);

    /**
     * returns username
     * @return username
     */
    String getUsername();

    /**
     * used to set email
     * @param email email
     */
    void setEmail(String email);

    /**
     * returns email
     * @return email
     */
    String getEmail();

    /**
     * used to set telephone number
     * @param telephoneNumber telephone number
     */
    void setTelephoneNumber(String telephoneNumber);

    /**
     * returns telephone number
     * @return telephone number
     */
    String getTelephoneNumber();

    /**
     * returns first name
     * @return first name
     */
    String getFirstName();

    /**
     * returns last name
     * @return last name
     */
    String getLastName();

    /**
     * used to set password
     * @param password the hashcode password
     */
    void setPassword(int password);

    /**
     * returns password
     * @return hashcode password
     */
    int getPassword();

    /**
     * returns birth
     * @return birth
     */
    LocalDate getBirth();

    /**
     * returns fiscal code
     * @return fiscal code
     */
    String getFiscalCode();

    /**
     * used to set id
     * @param id the id
     */
    void setId(String id);

    /**
     * returns id
     * @return id
     */
    String getId();

    /**
     * used to set city
     * @param city the city
     */
    void setCity(String city);

    /**
     * returns the city
     * @return the city
     */
    String getCity();

    /**
     * used to set address
     * @param address the address
     */
    void setAddress(String address);

    /**
     * returns address
     * @return address
     */
    String getAddress();

    /**
     * used to set the available to requests
     * @param bool available to requests
     */
    void setAvailableToRequests(boolean bool);

    /**
     * return available to request
     * @return available to request
     */
    boolean getAvailableToRequests();

    /**
     * returns feedback box
     * @return feedback box
     */
    FeedbackBox getFeedbackBox();

    /**
     * returns user simple notification
     * @return user simple notification
     */
    List<SimpleNotification<IUser>> getSimpleNotifications();

    /**
     * returns user meal notification
     * @return user meal notification
     */
    List<SubscriptionNotification<IUser, IMeal>> getMealNotifications();

    /**
     * returns user meal request notification
     * @return user meal request notification
     */
    List<SubscriptionNotification<IUser, IMealRequest>> getMealRequestNotifications();

}
