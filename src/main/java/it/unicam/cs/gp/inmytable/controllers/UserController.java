package it.unicam.cs.gp.inmytable.controllers;
import it.unicam.cs.gp.inmytable.allmeals.Food;
import it.unicam.cs.gp.inmytable.notification.SubscriptionManager;
import it.unicam.cs.gp.inmytable.persistence.*;
import it.unicam.cs.gp.inmytable.user.Feedback;
import it.unicam.cs.gp.inmytable.user.User;

import java.util.*;

/**
 * User controller
 */
public class UserController {
    private User user;
    private MealPersistence mealPersistence;
    private FeedbackPersistence feedbackPersistence;
    private UserPersistence userPersistence;
    private NotificationPersistence notificationPersistence;
    private SubscriptionManager subscriptionManager;


    /**
     * Build an UserController for the user
     * @param logUser logUser
     */
    public UserController(User logUser, MealPersistence mealPersistence, FeedbackPersistence feedbackPersistence, UserPersistence userPersistence, NotificationPersistence notificationPersistence){
        this.user = logUser;
        this.mealPersistence=mealPersistence;
        this.feedbackPersistence=feedbackPersistence;
        this.userPersistence=userPersistence;
        this.notificationPersistence=notificationPersistence;
        subscriptionManager = new SubscriptionManager();
    }

    public UserController(User logUser) throws Exception {
        this(logUser, new MealDB(), new FeedbackDB(), new UserDB(), new NotificationDB());
    }

    /**
     * Method for leave a comment to an other user
     * @param to  user who receive the feedback
     * @param rating    rating of the feedback
     * @param comment   comment of the feedback
     */
    public void leaveFeedback(User to, int rating, String comment, Food food) throws Exception {
        if (to ==null || comment == null) throw new NullPointerException("One parameter is null");
        if (user.equals(to)) throw new IllegalArgumentException("You cannot leave a comment to yourself!");
        Feedback feedback = new Feedback(this.user, to,rating,comment, food);
        this.user.getFeedbackBox().addFeedback(feedback);
        to.getFeedbackBox().addFeedback(feedback);
        feedbackPersistence.registerFeedback(feedback);
        notificationPersistence.registerSimpleNotification(subscriptionManager.createSimpleNotification(this.user, to, " ti ha lasciato un nuovo feedback"));

    }

    /**
     * This method is used to set users parameters
     * @param email user email
     * @param password user password
     * @param city user city
     * @param address user address
     * @param id user id
     * @param telephoneNumber user telephone number
     * @param available willingness to be contacted
     * @throws Exception
     */
    public void setUser(String email, String password, String city, String address, String id, String telephoneNumber, boolean available) throws Exception {
        if(email!=null)this.user.setEmail(email);
        if(password!=null)this.user.setPassword(password.hashCode());
        if(city!=null)this.user.setCity(city);
        if(address!=null)this.user.setAddress(address);
        if(id!=null)this.user.setId(id);
        if(telephoneNumber!=null)this.user.setTelephoneNumber(telephoneNumber);
        this.user.setAvailableToRequests(available);
        userPersistence.updateUser(this.user,this.user.getEmail(),this.user.getPassword(),this.user.getCity(),this.user.getAddress(),this.user.getId(),this.user.getTelephoneNumber(), this.user.getAvailableToRequests());
    }

    /**
     * returns users map
     * @return users map
     * @throws Exception
     */
    public Map<String, User> getUsers() throws Exception {
        return mealPersistence.getUsers();
    }

    /**
     * returns an user by username
     * @param username user username
     * @return user
     * @throws Exception
     */
    public User getUser(String username) throws Exception {
        return mealPersistence.getUsers().get(username);
    }

    /**
     * returns all users available to be contacted
     * @return  all users available to be contacted
     * @throws Exception
     */
    public Map<String, User> getAvailableToRequestUsers() throws Exception {
        Map<String, User> availableUsers = new HashMap<>();
        for(String key:mealPersistence.getUsers().keySet()){
            if(mealPersistence.getUsers().get(key).getAvailableToRequests()){
                availableUsers.put(key, mealPersistence.getUsers().get(key));
            }
        }
        return availableUsers;
    }
}

