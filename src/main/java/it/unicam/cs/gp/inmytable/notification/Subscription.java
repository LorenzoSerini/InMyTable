package it.unicam.cs.gp.inmytable.notification;

import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.allmeals.meals.MealStates;
import it.unicam.cs.gp.inmytable.user.User;

import java.util.Set;

public class Subscription implements Notification {

    private User host;
    private Meal meal;
    private NotificationStates state;
    private Set<Observer> observerSet;


    /**
     * Build a new Subscription for the host at the meal
     *
     * @param host user who wants to join the meal
     * @param meal meal
     */
    public Subscription(User host, Meal meal) {
        if (host == null || meal == null) throw new NullPointerException("All parameters must be different to null!");
        if (meal.getState() != MealStates.PENDING) throw new IllegalArgumentException("You cannot join to this meal!");
        this.host = host;
        this.meal = meal;
        if (meal.isFreeSubscription()) this.state = NotificationStates.ACCEPTED;
        else
            this.state = NotificationStates.PENDING;
        attach(meal.getHomeOwner().getNotificationManager());
        notifyObservers();
        attach(host.getNotificationManager());

    }


    @Override
    public void attach(Observer observer) {
        this.observerSet.add(observer);

    }

    @Override
    public void detach(Observer observer) {
        this.observerSet.remove(observer);

    }


    @Override
    public void notifyObservers() {
        for (Observer observer : observerSet) {
            observer.update();
        }

    }

    @Override
    public void accept() {
        if (this.state != NotificationStates.PENDING)
            throw new IllegalArgumentException("You cannot accept this subscription!");
        if (meal.getState() != MealStates.PENDING)
            throw new IllegalArgumentException("You cannot accept this subscription!");
        this.state = NotificationStates.ACCEPTED;
        notifyObservers();
    }

    @Override
    public void refuse() {
        if (this.state != NotificationStates.PENDING)
            throw new IllegalArgumentException("You cannot refuse this subscription!");
        this.state = NotificationStates.REFUSED;
        notifyObservers();
    }

    @Override
    public NotificationStates getNotificationState() {
        return null;
    }

    @Override
    public void setNotificationState(NotificationStates state) {
        this.state = state;
    }

    public User getHost() {
        return host;
    }

    public Meal getMeal() {
        return meal;
    }
}