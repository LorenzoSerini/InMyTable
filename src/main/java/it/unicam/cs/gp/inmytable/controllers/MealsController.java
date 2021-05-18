package it.unicam.cs.gp.inmytable.controllers;

import it.unicam.cs.gp.inmytable.allmeals.MealManager;
import it.unicam.cs.gp.inmytable.allmeals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.allmeals.PaymentType;
import it.unicam.cs.gp.inmytable.homewalls.HomeWall;
import it.unicam.cs.gp.inmytable.notification.ISubscription;
import it.unicam.cs.gp.inmytable.notification.SubscriptionManager;
import it.unicam.cs.gp.inmytable.persistence.MealDB;
import it.unicam.cs.gp.inmytable.persistence.MealPersistence;
import it.unicam.cs.gp.inmytable.persistence.NotificationDB;
import it.unicam.cs.gp.inmytable.persistence.NotificationPersistence;
import it.unicam.cs.gp.inmytable.user.IUser;
import it.unicam.cs.gp.inmytable.user.User;

import java.time.LocalDate;
import java.time.LocalTime;

public class MealsController {
    private User logUser;
    private MealManager mealManager;
    private MealPersistence mealPersistence;
   private SubscriptionManager subscriptionManager;


    public MealsController(User logUser, MealPersistence mealPersistence, NotificationPersistence notificationPersistence) throws Exception{
        mealManager = MealManager.getInstance();
        this.logUser = logUser;
        this.mealPersistence=mealPersistence;
        subscriptionManager = new SubscriptionManager();
        if(HomeWall.getInstance().getMealCatalog().isEmpty()) HomeWall.getInstance().getMealCatalog().addAll(mealPersistence.getMealsList());
        if(logUser.getMealNotifications().isEmpty()) logUser.getMealNotifications().addAll(notificationPersistence.getMealNotifications(logUser));
     //   System.out.println(logUser.getMealNotifications().size());
       // System.out.println(logUser.getMealNotifications().get(0).from().getUsername());
       // System.out.println(logUser.getMealNotifications().get(1).from().getUsername());
    }

    public MealsController(User logUser) throws Exception {
        this(logUser, new MealDB(), new NotificationDB());
    }

    public void cook(String date, String time, String expiryDate, String expiryTime, int maxNumUsers, String mealType, boolean freeSubscription, String place, ConsumationType consumationType, String description, String ingredients, PaymentType paymentType, String price) throws Exception {
        if (LocalDate.now().isAfter(LocalDate.parse(date)) || (LocalDate.now().isEqual(LocalDate.parse(date)) && LocalTime.now().isAfter(LocalTime.parse(time)) )) throw new IllegalArgumentException("You cannot travel in time");
        mealPersistence.registerMeal(
                mealManager.createMeal(logUser, maxNumUsers, LocalDate.parse(date), LocalTime.parse(time), LocalDate.parse(expiryDate), LocalTime.parse(expiryTime), mealType, freeSubscription, place, consumationType, description, ingredients, paymentType, price )
                );
    }

    public void joinToMeal(Meal meal) throws Exception {
        if(this.logUser.equals(meal.getHomeOwner())) throw new IllegalArgumentException("A host cannot sign up for his meal");
        if(meal.getUserList().contains(logUser)) throw new IllegalArgumentException("You can't sign up for the same meal twice");
        if (meal.isFreeSubscription()) {
            subscriptionManager.joinToMealNotification(this.logUser, meal.getHomeOwner(), meal, "Si è iscritto al pasto "+meal.getDescription()+" che si terrà il " + meal.getDate().toString() + " alle " + meal.getTime().toString());//("L'utente " + this.logUser.getUsername() + " si è iscritto al pasto che si terrà il " + meal.getDate().toString() + " alle " + meal.getTime().toString()));
        } else{
            subscriptionManager.joinToMealNotification(this.logUser, meal.getHomeOwner(), meal, "Si vorrebbe iscrivere al pasto "+meal.getDescription()+" che si terrà il " + meal.getDate().toString() + " alle " + meal.getTime().toString());//("L'utente " + this.logUser.getUsername() + " si vorrebbe iscrivere al pasto che si terrà il " + meal.getDate().toString() + " alle " + meal.getTime().toString()));
        }
        mealPersistence.registerUserToMeal(logUser,meal,meal.getHomeOwner().getMealNotifications().get(meal.getHomeOwner().getMealNotifications().size()-1));
    }


    public void acceptMealSubscription(ISubscription<IUser,IMeal> subscription){
        subscriptionManager.acceptMealNotification(this.logUser, subscription.getUser(), subscription, "L'utente " + this.logUser.getUsername() + " ha accettato la tua richiesta di iscrizione per il pasto che si terrà il "
                + subscription.getFood().getDate().toString() + " alle " + subscription.getFood().getTime().toString());
    }


    public void refuseMealSubscription(ISubscription<IUser,IMeal> subscription){
        subscriptionManager.refuseMealNotification(this.logUser, subscription.getUser(), subscription, "L'utente " + this.logUser.getUsername() + " ha rifiutato la tua richiesta di iscrizione per il pasto che si terrà il "
                + subscription.getFood().getDate().toString() + " alle " + subscription.getFood().getTime().toString());
    }


}