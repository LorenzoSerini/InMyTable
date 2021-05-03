package it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest;

import it.unicam.cs.gp.inmytable.allmeals.meals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.MealStates;
import it.unicam.cs.gp.inmytable.allmeals.meals.PaymentType;
import it.unicam.cs.gp.inmytable.notification.Notification;
import it.unicam.cs.gp.inmytable.notification.NotificationStates;
import it.unicam.cs.gp.inmytable.notification.Observer;
import it.unicam.cs.gp.inmytable.user.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * MealRequest from an host to one homeOwner or more.
 */
public abstract class MealRequest implements IMealRequest, Notification<User> {

    private LocalDate date;
    private LocalTime time;
    private LocalDate expiryDate;
    private LocalTime expiryTime;
    private String place;
    private String price;
    private int mealsNumber;
    private String allergy;
    private String mealType;
    private String description;
    private User host;
    private User homeOwner;
    private MealStates state;
    private ConsumationType consumationType;
    private PaymentType paymentType;
    private Set<Observer<User>> observers;
    private NotificationStates notificationState;

    /**
     * MealRequest constructor
     *
     * @param host       host who create the MealRequest
     * @param date       meal's date
     * @param expiryDate meal's expiring date
     * @param place      where host wants to eat
     */
    public MealRequest(User host, String mealType, ConsumationType consumationType, PaymentType payment, String description, LocalDate date, LocalTime time, LocalDate expiryDate, LocalTime expiryTime, String price, String place, String allergy, int mealsNumber) {
        if (LocalDate.now().isAfter(date) || (LocalDate.now().isEqual(date) && LocalTime.now().isAfter(time)))
            throw new IllegalArgumentException("You cannot travel in time");
        if (date.isBefore(expiryDate) || (LocalDate.now().isEqual(expiryDate) && time.isBefore(expiryTime)))
            throw new IllegalArgumentException("ExpirationTime should be after meal date");
        if (payment.compareTo(PaymentType.FREE) == 0) {
            this.price = "0";
        } else this.price = price;
        this.host = host;
        this.mealType = mealType;
        this.consumationType = consumationType;
        this.paymentType = payment;
        this.description = description;
        this.date = date;
        this.time = time;
        this.expiryDate = expiryDate;
        this.expiryTime = expiryTime;
        this.place = place;
        this.allergy = allergy;
        this.mealsNumber = mealsNumber;
        this.state = MealStates.PENDING;
        this.notificationState = NotificationStates.PENDING;
        this.observers = new HashSet<Observer<User>>();
        observers.add(host.getNotificationManager());
    }


    @Override
    public User getHost() {
        return host;
    }

    @Override
    public String getMealType() {
        return this.mealType;
    }

    @Override
    public User getHomeOwner() {
        return homeOwner;
    }

    @Override
    public void setHost(User host) {
        this.host = host;

    }

    @Override
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public LocalDate getDate() {
        return this.date;
    }

    @Override
    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public LocalTime getTime() {
        return this.time;
    }

    @Override
    public void setExpiringDate(LocalDate expiringDate) {
        this.expiryDate = expiringDate;
    }

    @Override
    public LocalDate getExpiryDate() {
        return this.expiryDate;
    }

    @Override
    public void setExpiryTime(LocalTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    @Override
    public LocalTime getExpiryTime() {
        return this.expiryTime;
    }

    @Override
    public void setHomeOwner(User homeOwner) {
        this.homeOwner = homeOwner;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getAllergy() {
        return allergy;
    }

    @Override
    public int getMealsNumber() {
        return mealsNumber;
    }


    public void setState(MealStates state) {
        this.state = state;
    }

    @Override
    public MealStates getState() {
        return state;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public void attach(Observer observer) {
        this.observers.add(observer);

    }

    @Override
    public void detach(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }

    }

    @Override
    public void accept(Observer<User> observer) {
        if (state != MealStates.PENDING) throw new IllegalArgumentException("The MealRequest is not Pending");
        if (notificationState != NotificationStates.PENDING)
            throw new IllegalArgumentException("The Request is not pending");
        if (this.getHomeOwner()!=null) throw new IllegalArgumentException("You cannot cook for this request!");
        setHomeOwner(observer.getObserver());
        setState(MealStates.FULL);
        this.notificationState = NotificationStates.ACCEPTED;
        notifyObservers();

    }

    @Override
    abstract public void refuse(Observer<User> observer);

    @Override
    public NotificationStates getNotificationState() {
        return notificationState;
    }

    @Override
    public void setNotificationState(NotificationStates state){
        this.notificationState = state;
    }

}