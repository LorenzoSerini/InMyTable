package it.unicam.cs.gp.inmytable.controllers;

import it.unicam.cs.gp.inmytable.allmeals.ConsummationType;
import it.unicam.cs.gp.inmytable.allmeals.MealManager;
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
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Meals controller
 */
public class MealsController {
    private User logUser;
    private MealManager mealManager;
    private MealPersistence mealPersistence;
   private SubscriptionManager subscriptionManager;
   private NotificationPersistence notificationPersistence;


    public MealsController(User logUser, MealPersistence mealPersistence, NotificationPersistence notificationPersistence) throws Exception{
        mealManager = MealManager.getInstance();
        this.logUser = logUser;
        this.mealPersistence=mealPersistence;
        this.notificationPersistence=notificationPersistence;
        subscriptionManager = new SubscriptionManager();
        if(HomeWall.getInstance().getMealCatalog().isEmpty()) HomeWall.getInstance().getMealCatalog().addAll(mealPersistence.getMealsList());
    }

    public MealsController(User logUser) throws Exception {
        this(logUser, new MealDB(), new NotificationDB());
    }

    /**
     * This method is used to cook and post a meal
     * @param date meal date
     * @param time meal time
     * @param expiryDate meal expiry date
     * @param expiryTime meal expiry time
     * @param maxNumUsers maximum number user for this meal
     * @param mealType meal type
     * @param freeSubscription meal subscription is free or not
     * @param city meal city
     * @param place meal place
     * @param consummationType meal consummation type
     * @param description meal description
     * @param ingredients meal ingredients
     * @param paymentType meal payment
     * @param price meal price
     * @throws Exception
     */
    public void cook(String date, String time, String expiryDate, String expiryTime, int maxNumUsers, String mealType, boolean freeSubscription, String city, String place, ConsummationType consummationType, String description, String ingredients, PaymentType paymentType, String price) throws Exception {
        if (LocalDate.now().isAfter(LocalDate.parse(date)) || (LocalDate.now().isEqual(LocalDate.parse(date)) && LocalTime.now().isAfter(LocalTime.parse(time)) )) throw new TimeTravelException("date must be after " + LocalDate.now().toString() + " at " + LocalTime.now().toString());
        mealPersistence.registerMeal(
                mealManager.createMeal(logUser, maxNumUsers, LocalDate.parse(date), LocalTime.parse(time), LocalDate.parse(expiryDate), LocalTime.parse(expiryTime), mealType, freeSubscription, city, place, consummationType, description, ingredients, paymentType, price )
                );
    }

    /**
     * This method is used to subscribe this user to a meal
     * @param meal the meal to which he must be registered
     * @throws Exception
     */
    public void joinToMeal(Meal meal) throws Exception {
        if(this.logUser.equals(meal.getHomeOwner())) throw new IllegalArgumentException("A host cannot sign up for his meal");
        if(meal.getUserList().contains(logUser)) throw new IllegalArgumentException("You can't sign up for the same meal twice");
        if (meal.isFreeSubscription()) {
            subscriptionManager.joinToMealNotification(this.logUser, meal.getHomeOwner(), meal, "si è iscritto al pasto "+meal.getDescription()+" che si terrà il " + meal.getDate().toString() + " alle " + meal.getTime().toString());
            notificationPersistence.registerSimpleNotification(subscriptionManager.createSimpleNotification(this.logUser, this.logUser, ".Ti sei iscritto al pasto"+meal.getDescription()+" che si terrà il " + meal.getDate().toString() + " alle " + meal.getTime().toString()));
        } else{
            subscriptionManager.joinToMealNotification(this.logUser, meal.getHomeOwner(), meal, "si vorrebbe iscrivere al pasto "+meal.getDescription()+" che si terrà il " + meal.getDate().toString() + " alle " + meal.getTime().toString());
        }
        mealPersistence.registerUserToMeal(logUser,meal,meal.getHomeOwner().getMealNotifications().get(meal.getHomeOwner().getMealNotifications().size()-1));
    }


    /**
     * This method is used to accepted an user to a not free subscription meal
     * @param subscription the meal subscription created when the user made the request
     * @throws Exception
     */
    public void acceptMealSubscription(ISubscription<IUser,IMeal> subscription) throws Exception {
        subscriptionManager.acceptMealNotification(this.logUser, subscription.getUser(), subscription, "ha accettato la tua richiesta di iscrizione per il pasto "+subscription.getFood().getDescription()+" che si terrà il "
                + subscription.getFood().getDate().toString() + " alle " + subscription.getFood().getTime().toString());
        mealPersistence.acceptOrRefuseMealSubscription(subscription.getFood(),  subscription.getUser().getMealNotifications().get(subscription.getUser().getMealNotifications().size()-1));
    }

    /**
     * This method is used to refuse an user to a not free subscription meal
     * @param subscription the meal subscription created when the user made the request
     * @throws Exception
     */
    public void refuseMealSubscription(ISubscription<IUser,IMeal> subscription) throws Exception {
        subscriptionManager.refuseMealNotification(this.logUser, subscription.getUser(), subscription, "ha rifiutato la tua richiesta di iscrizione per il pasto "+subscription.getFood().getDescription()+" che si terrà il "
                + subscription.getFood().getDate().toString() + " alle " + subscription.getFood().getTime().toString());
        mealPersistence.acceptOrRefuseMealSubscription(subscription.getFood(), subscription.getUser().getMealNotifications().get(subscription.getUser().getMealNotifications().size()-1));
    }

    /**
     * returns a meal by id
     * @param id the meal id
     * @return a meal by id
     */
    public IMeal getMeal(String id){
        return mealPersistence.getMealsMap().get(id);
    }

    /**
     * returns all meals published by this user
     * @return all meals published by this user
     * @throws Exception
     */
    public List<Meal> showPublishedMeals() throws Exception {
        return mealPersistence.getMealsList().stream().filter(p->p.getHomeOwner()!=null).filter(p -> p.getHomeOwner().equals(this.logUser)).collect(Collectors.toList());
    }

    /**
     * returns meals published by this user
     * @param predicate filter by predicate
     * @return meals published by this user
     * @throws Exception
     */
    public List<Meal> showPublishedMeals(Predicate<Meal> predicate) throws Exception {
        return mealPersistence.getMealsList().stream().filter(p->p.getHomeOwner()!=null).filter(p -> p.getHomeOwner().equals(this.logUser)).filter(predicate).collect(Collectors.toList());
    }

    /**
     * returns all meals attended by this user
     * @return all meals attended by this user
     * @throws Exception
     */
    public List<Meal> showAttendedMeals() throws Exception {
        return mealPersistence.getMealsList().stream().filter(p->p.getUserList().contains(this.logUser)).collect(Collectors.toList());
    }

    /**
     * returns meals attended by this user
     * @param predicate filter by predicate
     * @return meals attended by this user
     * @throws Exception
     */
    public List<Meal> showAttendedMeals(Predicate<Meal>predicate) throws Exception {
        return mealPersistence.getMealsList().stream().filter(p->p.getUserList().contains(this.logUser)).filter(predicate).collect(Collectors.toList());
    }


}