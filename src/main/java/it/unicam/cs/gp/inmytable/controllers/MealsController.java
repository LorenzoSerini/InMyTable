package it.unicam.cs.gp.inmytable.controllers;

import it.unicam.cs.gp.inmytable.allmeals.MealManager;
import it.unicam.cs.gp.inmytable.allmeals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.allmeals.PaymentType;
import it.unicam.cs.gp.inmytable.exception.TimeTravelException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
    }

    public MealsController(User logUser) throws Exception {
        this(logUser, new MealDB(), new NotificationDB());
    }

    public void cook(String date, String time, String expiryDate, String expiryTime, int maxNumUsers, String mealType, boolean freeSubscription, String place, ConsumationType consumationType, String description, String ingredients, PaymentType paymentType, String price) throws Exception {
        if (LocalDate.now().isAfter(LocalDate.parse(date)) || (LocalDate.now().isEqual(LocalDate.parse(date)) && LocalTime.now().isAfter(LocalTime.parse(time)) )) throw new TimeTravelException("date must be after " + LocalDate.now().toString() + " at " + LocalTime.now().toString());
        mealPersistence.registerMeal(
                mealManager.createMeal(logUser, maxNumUsers, LocalDate.parse(date), LocalTime.parse(time), LocalDate.parse(expiryDate), LocalTime.parse(expiryTime), mealType, freeSubscription, place, consumationType, description, ingredients, paymentType, price )
                );
    }

    public void joinToMeal(Meal meal) throws Exception {
        if(this.logUser.equals(meal.getHomeOwner())) throw new IllegalArgumentException("A host cannot sign up for his meal");
        if(meal.getUserList().contains(logUser)) throw new IllegalArgumentException("You can't sign up for the same meal twice");
        if (meal.isFreeSubscription()) {
            subscriptionManager.joinToMealNotification(this.logUser, meal.getHomeOwner(), meal, "si è iscritto al pasto "+meal.getDescription()+" che si terrà il " + meal.getDate().toString() + " alle " + meal.getTime().toString());//("L'utente " + this.logUser.getUsername() + " si è iscritto al pasto che si terrà il " + meal.getDate().toString() + " alle " + meal.getTime().toString()));
        } else{
            subscriptionManager.joinToMealNotification(this.logUser, meal.getHomeOwner(), meal, "si vorrebbe iscrivere al pasto "+meal.getDescription()+" che si terrà il " + meal.getDate().toString() + " alle " + meal.getTime().toString());//("L'utente " + this.logUser.getUsername() + " si vorrebbe iscrivere al pasto che si terrà il " + meal.getDate().toString() + " alle " + meal.getTime().toString()));
        }
        mealPersistence.registerUserToMeal(logUser,meal,meal.getHomeOwner().getMealNotifications().get(meal.getHomeOwner().getMealNotifications().size()-1));
    }


    public void acceptMealSubscription(ISubscription<IUser,IMeal> subscription) throws Exception {
        subscriptionManager.acceptMealNotification(this.logUser, subscription.getUser(), subscription, "ha accettato la tua richiesta di iscrizione per il pasto "+subscription.getFood().getDescription()+" che si terrà il "
                + subscription.getFood().getDate().toString() + " alle " + subscription.getFood().getTime().toString());
        mealPersistence.acceptOrRefuseMealSubscription(subscription.getFood(),  subscription.getUser().getMealNotifications().get(subscription.getUser().getMealNotifications().size()-1));
    }


    public void refuseMealSubscription(ISubscription<IUser,IMeal> subscription) throws Exception {
        subscriptionManager.refuseMealNotification(this.logUser, subscription.getUser(), subscription, "ha rifiutato la tua richiesta di iscrizione per il pasto "+subscription.getFood().getDescription()+" che si terrà il "
                + subscription.getFood().getDate().toString() + " alle " + subscription.getFood().getTime().toString());
        mealPersistence.acceptOrRefuseMealSubscription(subscription.getFood(), subscription.getUser().getMealNotifications().get(subscription.getUser().getMealNotifications().size()-1));
    }


   /* public List<Meal> showPublishedMeals() throws Exception {
        List<Meal> meals = new ArrayList<>();
        for(Meal meal:mealPersistence.getMealsList()){
            if(meal.getHomeOwner().equals(this.logUser)) meals.add(meal);
        }
        return meals;
    }

    public List<Meal> showAttendedMeals() throws Exception {
        List<Meal> meals = new ArrayList<>();
        for(Meal meal:mealPersistence.getMealsList()){
            for(IUser u:meal.getUserList()){
                if(u.equals(this.logUser)) meals.add(meal);
            }
        }
        return meals;
    }*/


    public IMeal getMeal(String id){
        return mealPersistence.getMealsMap().get(id);
    }


    public List<Meal> showPublishedMeals() throws Exception {
        return mealPersistence.getMealsList().stream().filter(p->p.getHomeOwner()!=null).filter(p -> p.getHomeOwner().equals(this.logUser)).collect(Collectors.toList());
    }

    public List<Meal> showPublishedMeals(Predicate<Meal> predicate) throws Exception {
        return mealPersistence.getMealsList().stream().filter(p->p.getHomeOwner()!=null).filter(p -> p.getHomeOwner().equals(this.logUser)).filter(predicate).collect(Collectors.toList());
    }

    public List<Meal> showAttendedMeals() throws Exception {
        return mealPersistence.getMealsList().stream().filter(p->p.getUserList().contains(this.logUser)).collect(Collectors.toList());
    }

    public List<Meal> showAttendedMeals(Predicate<Meal>predicate) throws Exception {
        return mealPersistence.getMealsList().stream().filter(p->p.getUserList().contains(this.logUser)).filter(predicate).collect(Collectors.toList());
    }


}