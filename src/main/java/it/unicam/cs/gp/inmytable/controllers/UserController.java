package it.unicam.cs.gp.inmytable.controllers;
import it.unicam.cs.gp.inmytable.allmeals.Food;
import it.unicam.cs.gp.inmytable.notification.SubscriptionManager;
import it.unicam.cs.gp.inmytable.persistence.*;
import it.unicam.cs.gp.inmytable.user.Feedback;
import it.unicam.cs.gp.inmytable.user.User;

import java.util.*;

public class UserController {
    private User user;
    private MealPersistence mealPersistence;
    private FeedbackPersistence feedbackPersistence;
    private UserPersistence userPersistence;


    /**
     * Build an UserController for the user
     * @param logUser logUser
     */
    public UserController(User logUser, MealPersistence mealPersistence, FeedbackPersistence feedbackPersistence, UserPersistence userPersistence){
        this.user = logUser;
        this.mealPersistence=mealPersistence;
        this.feedbackPersistence=feedbackPersistence;
       this.userPersistence=userPersistence;
    }

    public UserController(User logUser) throws Exception {
        this(logUser, new MealDB(), new FeedbackDB(), new UserDB());
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
    }


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


    public Map<String, User> getUsers() throws Exception {
        return mealPersistence.getUsers();
    }

    public User getUser(String username) throws Exception {
        return mealPersistence.getUsers().get(username);
    }

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

