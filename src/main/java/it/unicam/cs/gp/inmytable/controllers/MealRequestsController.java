package it.unicam.cs.gp.inmytable.controllers;

import it.unicam.cs.gp.inmytable.allmeals.MealManager;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.IMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequestType;
import it.unicam.cs.gp.inmytable.allmeals.meals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.PaymentType;
import it.unicam.cs.gp.inmytable.homewalls.HomeWall;
import it.unicam.cs.gp.inmytable.notification.ISubscription;
import it.unicam.cs.gp.inmytable.notification.MealRequestSubscription;
import it.unicam.cs.gp.inmytable.notification.SubscriptionManager;
import it.unicam.cs.gp.inmytable.notification.SubscriptionNotification;
import it.unicam.cs.gp.inmytable.persistence.MealDB;
import it.unicam.cs.gp.inmytable.persistence.MealRequestDB;
import it.unicam.cs.gp.inmytable.persistence.MealRequestPersistence;
import it.unicam.cs.gp.inmytable.user.IUser;
import it.unicam.cs.gp.inmytable.user.User;

import java.time.LocalDate;
import java.time.LocalTime;

public class MealRequestsController {
    private User logUser;
    private MealManager mealManager;
    private MealRequestPersistence mealRequestPersistence;
    private SubscriptionManager subscriptionManager;



    public MealRequestsController(User logUser, MealRequestPersistence mealRequestPersistence) throws Exception {
       this.logUser=logUser;
        mealManager = MealManager.getInstance();
        this.mealRequestPersistence=mealRequestPersistence;
        subscriptionManager = new SubscriptionManager();
        if(HomeWall.getInstance().getMealRequestCatalog().isEmpty()) HomeWall.getInstance().getMealRequestCatalog().addAll(mealRequestPersistence.getPublicMealsRequestMap());
    }

    public MealRequestsController(User logUser) throws Exception {
        this(logUser, new MealRequestDB());
    }

    public void postPublicMealRequest(String description, String mealType, ConsumationType consumationType, PaymentType payment, String date, String time, String expiryDate, String expiryTime, String price, String place, String allergy, int mealsNumber) throws Exception {
        if (LocalDate.now().isAfter(LocalDate.parse(date)) || (LocalDate.now().isEqual(LocalDate.parse(date)) && LocalTime.now().isAfter(LocalTime.parse(time))))
            throw new IllegalArgumentException("You cannot travel in time");
        MealRequest publicMealRequest = mealManager.createPublicMealRequest(logUser, mealType, consumationType, payment, description, LocalDate.parse(date), LocalTime.parse(time),
                LocalDate.parse(expiryDate), LocalTime.parse(expiryTime), price, place, allergy, mealsNumber );
        mealRequestPersistence.registerMealRequest(publicMealRequest);
    }


    public void postPrivateMealRequest(User homeOwner, String description, String mealType, ConsumationType consumationType, PaymentType payment, String date, String time, String expiryDate, String expiryTime, String price, String place, String allergy, int mealsNumber) throws Exception {
        if (LocalDate.now().isAfter(LocalDate.parse(date)) || (LocalDate.now().isEqual(LocalDate.parse(date)) && LocalTime.now().isAfter(LocalTime.parse(time))))
            throw new IllegalArgumentException("You cannot travel in time");
        MealRequest privateMealRequest = mealManager.createPrivateMealRequest(logUser, mealType, consumationType, payment, description, LocalDate.parse(date), LocalTime.parse(time),
                LocalDate.parse(expiryDate), LocalTime.parse(expiryTime), price, place, allergy, mealsNumber, homeOwner );
        subscriptionManager.sendPrivateRequestNotification(this.logUser, homeOwner, privateMealRequest, "L'utente " + this.logUser.getUsername() + " ti ha richiesto un pasto per il " + privateMealRequest.getDate().toString() + " alle " + privateMealRequest.getTime().toString());
        mealRequestPersistence.registerMealRequest(privateMealRequest);
    }


    public void acceptPublicMealRequest(MealRequest mealRequest) throws Exception {
        if(mealRequest.getType().equals(MealRequestType.PUBLIC)) {
            subscriptionManager.acceptPublicRequestNotification(this.logUser, mealRequest.getHost(), mealRequest, "L'utente " + this.logUser.getUsername() + " ha accettato la tua richiesta di pasto pubblico che si terrà il " + mealRequest.getDate().toString() + " alle " + mealRequest.getTime().toString());
            mealRequestPersistence.registerHomeOwnerToMealRequest(this.logUser, mealRequest, mealRequest.getHost().getMealRequestNotifications().get(mealRequest.getHost().getMealRequestNotifications().size()-1));
        }else throw new IllegalArgumentException("This is a private meal request");
    }



    public void acceptPrivateMealRequest(ISubscription<IUser,IMealRequest> subscription){
        if(subscription.getFood().getType().equals(MealRequestType.PRIVATE)) {
            subscriptionManager.acceptPrivateRequestNotification(this.logUser, subscription.getFood().getHost(), subscription, "L'utente " + this.logUser.getUsername() + " ha accettato la tua richiesta privato di un pasto per il "
                    + subscription.getFood().getDate().toString() + " alle " + subscription.getFood().getTime().toString() );
        }else throw new IllegalArgumentException("This is a public meal request");
    }



    public void refusePrivateMealRequest(ISubscription<IUser,IMealRequest> subscription){
        if(subscription.getFood().getType().equals(MealRequestType.PRIVATE)) {
            subscriptionManager.refusePrivateRequestNotification(this.logUser, subscription.getFood().getHost(), subscription, "L'utente " + this.logUser.getUsername() + " ha rifiutato la tua richiesta privato di un pasto per il "
                    + subscription.getFood().getDate().toString() + " alle " + subscription.getFood().getTime().toString() );
        }else throw new IllegalArgumentException("This is a public meal request");
    }

}
