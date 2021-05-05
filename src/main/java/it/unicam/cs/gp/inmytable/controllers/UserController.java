package it.unicam.cs.gp.inmytable.controllers;

import it.unicam.cs.gp.inmytable.allmeals.MealManager;
import it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.homewalls.HomeWall;
import it.unicam.cs.gp.inmytable.notification.Notification;
import it.unicam.cs.gp.inmytable.notification.NotificationStates;
import it.unicam.cs.gp.inmytable.notification.Subscription;
import it.unicam.cs.gp.inmytable.notification.SubscriptionManager;
import it.unicam.cs.gp.inmytable.persistence.MealDB;
import it.unicam.cs.gp.inmytable.persistence.MealPersistence;
import it.unicam.cs.gp.inmytable.user.Feedback;
import it.unicam.cs.gp.inmytable.user.User;
import it.unicam.cs.gp.inmytable.utility.UsersUtilities;

import java.util.*;
import java.util.stream.Collectors;

public class UserController {
    private User user;
    private SubscriptionManager subscriptionManager;
    private MealPersistence mealPersistence;

    /**
     * Build an UserController for the user
     * @param logUser logUser
     */
    public UserController(User logUser, MealPersistence mealPersistence){
        this.user = logUser;
        this.subscriptionManager = SubscriptionManager.getInstance();
        this.mealPersistence=mealPersistence;
    }

    public UserController(User logUser) throws Exception {
        this(logUser, new MealDB());
    }

    /**
     * Method for leave a comment to an other user
     * @param to  user who receive the feedback
     * @param rating    rating of the feedback
     * @param comment   comment of the feedback
     */
    public void leaveFeedback(User to, int rating, String comment){
        if (to ==null || comment == null) throw new NullPointerException("One parameter is null");
        if (user.equals(to)) throw new IllegalArgumentException("You cannot leave a comment to yourself!");
        to.getFeedbackBox().addFeedback(new Feedback(this.user, to,rating,comment));
    }

    public void joinToMeal(Meal meal) throws Exception{
        if(this.user==meal.getHomeOwner()) throw new IllegalArgumentException("A host cannot sign up for his meal");
        if(meal.getUserList().contains(user)) throw new IllegalArgumentException("You can't sign up for the same meal twice");
        Subscription subscription = MealManager.getInstance().joinToMeal(user, meal);
        if (subscription.getNotificationState()== NotificationStates.ACCEPTED){meal.addUser(user);}
        meal.getHomeOwner().getNotificationManager().addNotification(subscription);
        mealPersistence.registerUserToMeal(user, meal, subscription);
    }

    public List<Notification> showNotification(){
        return user.getNotificationManager().getNotificationSet();
    }

    public void acceptSubscription(Subscription subscription) throws Exception{
        subscriptionManager.acceptSubscription(user, subscription);
        //user.getNotificationManager().getNotificationSet().remove(subscription);
        subscription.getHost().getNotificationManager().addNotification(subscription);
    }

    public void refuseSubscription(Subscription subscription) throws Exception{
        subscriptionManager.refuseSubscription(user, subscription);
        //user.getNotificationManager().getNotificationSet().remove(subscription);
        subscription.getHost().getNotificationManager().addNotification(subscription);
    }

    public void cookMealRequest(MealRequest mealRequest) throws Exception{
        if (user.equals(mealRequest.getHost())) throw new IllegalArgumentException("You are the Host!");
        subscriptionManager.cookMealRequest(user, mealRequest);
        mealRequest.getHost().getNotificationManager().addNotification(mealRequest);
    }

    /**
     * Returns the list of all meals the user has attended or published
     * @return list of meals
     */
    public List<Meal> mealsHistory(){
        return MealManager.getInstance().mealsHistory(user);
    }

    /**
     * returns the list of all meals request the user has attended or published
     * @return list of mealRequest
     */
    public List<MealRequest> mealRequestHistory(){
        return MealManager.getInstance().mealRequestHistory(user);
    }

    /**
     * Return the list of pending notification
     * @return list of pending Notification
     */
    public List<Notification> showPendingNotification(){
        return showNotification().stream().filter(p-> p.getNotificationState()==NotificationStates.PENDING).collect(Collectors.toList());
    }

    /**
     * Method for accept a notification
     * @param notification to accept
     */
    public void acceptNotification(Notification notification) throws Exception{
        subscriptionManager.acceptNotification(user, notification);
    }

    /**
     * Method for refuse a notification
     * @param notification to refuse
     * @throws Exception if user doesn't respect notification's rules
     */
    public void refuseNotification(Notification notification) throws Exception{
        subscriptionManager.refuseNotification(user, notification);
    }

    public Map<String, User> getUsers(){
        return UsersUtilities.getInstance().getUsers();
    }

    public User getUser(String username){return UsersUtilities.getInstance().getUser(username);}

    public Map<String, User> getAvailableToRequestUsers(){
        Map<String, User> availableUsers = new HashMap<>();
        for(String key:UsersUtilities.getInstance().getUsers().keySet()){
            if(UsersUtilities.getInstance().getUser(key).getAvailableToRequests()){
                availableUsers.put(key, UsersUtilities.getInstance().getUser(key));
            }
        }
        return availableUsers;
    }
}

