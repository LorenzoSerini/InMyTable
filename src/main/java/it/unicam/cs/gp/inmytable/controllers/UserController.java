package it.unicam.cs.gp.inmytable.controllers;
import it.unicam.cs.gp.inmytable.notification.SubscriptionManager;
import it.unicam.cs.gp.inmytable.persistence.FeedbackDB;
import it.unicam.cs.gp.inmytable.persistence.FeedbackPersistence;
import it.unicam.cs.gp.inmytable.persistence.MealDB;
import it.unicam.cs.gp.inmytable.persistence.MealPersistence;
import it.unicam.cs.gp.inmytable.user.Feedback;
import it.unicam.cs.gp.inmytable.user.User;

import java.util.*;

public class UserController {
    private User user;
    private MealPersistence mealPersistence;
    private FeedbackPersistence feedbackPersistence;
    private SubscriptionManager subscriptionManager;

    /**
     * Build an UserController for the user
     * @param logUser logUser
     */
    public UserController(User logUser, MealPersistence mealPersistence, FeedbackPersistence feedbackPersistence){
        this.user = logUser;
        this.mealPersistence=mealPersistence;
        this.feedbackPersistence=feedbackPersistence;
        subscriptionManager = new SubscriptionManager();
    }

    public UserController(User logUser) throws Exception {
        this(logUser, new MealDB(), new FeedbackDB());
    }

    /**
     * Method for leave a comment to an other user
     * @param to  user who receive the feedback
     * @param rating    rating of the feedback
     * @param comment   comment of the feedback
     */
    public void leaveFeedback(User to, int rating, String comment) throws Exception {
        if (to ==null || comment == null) throw new NullPointerException("One parameter is null");
        if (user.equals(to)) throw new IllegalArgumentException("You cannot leave a comment to yourself!");
        Feedback feedback = new Feedback(this.user, to,rating,comment);
        this.user.getFeedbackBox().addFeedback(feedback);
        to.getFeedbackBox().addFeedback(feedback);
        feedbackPersistence.registerFeedback(feedback);
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

