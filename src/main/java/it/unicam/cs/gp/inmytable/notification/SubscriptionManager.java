package it.unicam.cs.gp.inmytable.notification;

import it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.allmeals.meals.MealStates;
import it.unicam.cs.gp.inmytable.user.User;

public class SubscriptionManager {

    private static SubscriptionManager subscriptionManager;


    private SubscriptionManager(){

    }

    public static SubscriptionManager getInstance(){
        if (subscriptionManager==null)
            subscriptionManager = new SubscriptionManager();
        return subscriptionManager;
    }

    /**
     * Method for accept one subscription
     * @param subscription  subscription to accept
     * @throws Exception    if the user is not the homeOwner in the meal
     */
    public void acceptSubscription(User user, Subscription subscription) throws Exception{
        subscription.detach(user.getNotificationManager());
        subscription.accept(user.getNotificationManager());
        subscription.getMeal().addUser(subscription.getHost());

    }

    /**
     * Method for refuse one subscription
     * @param subscription  subscription to refuse
     * @throws Exception    if the user is not the homeOwner in the meal
     */
    public void refuseSubscription(User user, Subscription subscription) throws Exception{
        if (!subscription.getMeal().getHomeOwner().equals(user)) throw new IllegalArgumentException("You cannot accept this subscription");
        subscription.detach(user.getNotificationManager());
        subscription.refuse(user.getNotificationManager());
    }

    public void cookMealRequest(User user, MealRequest mealRequest) throws Exception{
        if (mealRequest.getState()!= MealStates.PENDING || mealRequest.getHomeOwner()!=null) throw new IllegalArgumentException("You cannot cook for this request!");
        mealRequest.accept(user.getNotificationManager());
        mealRequest.setHomeOwner(user);
    }

    /**
     * Method for accepting a notification
     * @param user user who wants accept a notification
     * @param notification to accept
     */
    public void acceptNotification(User user, Notification notification) throws Exception{
        notification.accept(user.getNotificationManager());
    }

    /**
     * Method for refuse a notification
     * @param user  user who wants refuse a notification
     * @param notification  to refuse
     */
    public void refuseNotification(User user, Notification notification)throws Exception{
        notification.refuse(user.getNotificationManager());
    }
}