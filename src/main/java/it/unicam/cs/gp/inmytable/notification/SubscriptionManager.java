package it.unicam.cs.gp.inmytable.notification;

import it.unicam.cs.gp.inmytable.user.User;

public class SubscriptionManager {

    private User user;

    /**
     * Build a new SubscriptionManager for the user
     * @param user  who wants to menage Subscriptions
     */
    public SubscriptionManager(User user){
        this.user = user;
    }

    /**
     * Method for accept one subscription
     * @param subscription  subscription to accept
     * @throws Exception    if the user is not the homeOwner in the meal
     */
    public void acceptSubscription(Subscription subscription) throws Exception{
        if (!subscription.getMeal().getHomeOwner().equals(user)) throw new IllegalArgumentException("You cannot accept this subscription");
        subscription.detach(user.getNotificationManager());
        subscription.accept();
        subscription.getMeal().addUser(subscription.getHost());
    }

    /**
     * Method for refuse one subscription
     * @param subscription  subscription to refuse
     * @throws Exception    if the user is not the homeOwner in the meal
     */
    public void refuseSubscription(Subscription subscription) throws Exception{
        if (!subscription.getMeal().getHomeOwner().equals(user)) throw new IllegalArgumentException("You cannot accept this subscription");
        subscription.detach(user.getNotificationManager());
        subscription.refuse();
    }
}