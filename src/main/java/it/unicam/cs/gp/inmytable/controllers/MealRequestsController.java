package it.unicam.cs.gp.inmytable.controllers;

import it.unicam.cs.gp.inmytable.allmeals.MealManager;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.IMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequestType;
import it.unicam.cs.gp.inmytable.allmeals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.PaymentType;
import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.homewalls.HomeWall;
import it.unicam.cs.gp.inmytable.notification.ISubscription;
import it.unicam.cs.gp.inmytable.notification.MealRequestSubscription;
import it.unicam.cs.gp.inmytable.notification.SubscriptionManager;
import it.unicam.cs.gp.inmytable.notification.SubscriptionNotification;
import it.unicam.cs.gp.inmytable.persistence.*;
import it.unicam.cs.gp.inmytable.user.IUser;
import it.unicam.cs.gp.inmytable.user.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealRequestsController {
    private User logUser;
    private MealManager mealManager;
    private MealRequestPersistence mealRequestPersistence;
    private SubscriptionManager subscriptionManager;



    public MealRequestsController(User logUser, MealRequestPersistence mealRequestPersistence, NotificationPersistence notificationPersistence) throws Exception {
       this.logUser=logUser;
        mealManager = MealManager.getInstance();
        this.mealRequestPersistence=mealRequestPersistence;
        subscriptionManager = new SubscriptionManager();
        if(HomeWall.getInstance().getMealRequestCatalog().isEmpty()) HomeWall.getInstance().getMealRequestCatalog().addAll(mealRequestPersistence.getPublicMealsRequestList());
    }

    public MealRequestsController(User logUser) throws Exception {
        this(logUser, new MealRequestDB(), new NotificationDB());
    }

    public void postPublicMealRequest(String description, String mealType, ConsumationType consumationType, PaymentType payment, String date, String time, String expiryDate, String expiryTime, String price, String place, String allergy, int mealsNumber) throws Exception {
        if (LocalDate.now().isAfter(LocalDate.parse(date)) || (LocalDate.now().isEqual(LocalDate.parse(date)) && LocalTime.now().isAfter(LocalTime.parse(time))))
            throw new IllegalArgumentException("You cannot travel in time");
        MealRequest publicMealRequest = mealManager.createPublicMealRequest(logUser, mealType, consumationType, payment, description, LocalDate.parse(date), LocalTime.parse(time),
                LocalDate.parse(expiryDate), LocalTime.parse(expiryTime), price, place, allergy, mealsNumber );
        mealRequestPersistence.registerPublicMealRequest(publicMealRequest);
    }


    public void postPrivateMealRequest(User homeOwner, String description, String mealType, ConsumationType consumationType, PaymentType payment, String date, String time, String expiryDate, String expiryTime, String price, String place, String allergy, int mealsNumber) throws Exception {
        if (LocalDate.now().isAfter(LocalDate.parse(date)) || (LocalDate.now().isEqual(LocalDate.parse(date)) && LocalTime.now().isAfter(LocalTime.parse(time))))
            throw new IllegalArgumentException("You cannot travel in time");
        MealRequest privateMealRequest = mealManager.createPrivateMealRequest(logUser, mealType, consumationType, payment, description, LocalDate.parse(date), LocalTime.parse(time),
                LocalDate.parse(expiryDate), LocalTime.parse(expiryTime), price, place, allergy, mealsNumber, homeOwner );
        subscriptionManager.sendPrivateRequestNotification(this.logUser, homeOwner, privateMealRequest, "ti ha richiesto un pasto per il " + privateMealRequest.getDate().toString() + " alle " + privateMealRequest.getTime().toString());
        mealRequestPersistence.registerPrivateMealRequest(privateMealRequest, homeOwner.getMealRequestNotifications().get(homeOwner.getMealRequestNotifications().size()-1));
    }


    public void acceptPublicMealRequest(MealRequest mealRequest) throws Exception {
        if(mealRequest.getType().equals(MealRequestType.PUBLIC)) {
            mealRequest.setHomeOwner(this.logUser);
            subscriptionManager.acceptPublicRequestNotification(this.logUser, mealRequest.getHost(), mealRequest, "ha accettato la tua richiesta di pasto pubblico che si terrà il " + mealRequest.getDate().toString() + " alle " + mealRequest.getTime().toString());
            mealRequestPersistence.registerHomeOwnerToMealRequest(this.logUser, mealRequest, mealRequest.getHost().getMealRequestNotifications().get(mealRequest.getHost().getMealRequestNotifications().size() - 1));
        }else throw new IllegalArgumentException("This is a private meal request");
    }



    public void acceptPrivateMealRequest(ISubscription<IUser,IMealRequest> subscription) throws Exception {
        if(subscription.getFood().getType().equals(MealRequestType.PRIVATE)) {
            subscriptionManager.acceptPrivateRequestNotification(this.logUser, subscription.getFood().getHost(), subscription, "ha accettato la tua richiesta privato di un pasto per il "
                    + subscription.getFood().getDate().toString() + " alle " + subscription.getFood().getTime().toString() );
            mealRequestPersistence.registerHomeOwnerToMealRequest(this.logUser, subscription.getFood(), subscription.getFood().getHost().getMealRequestNotifications().get(subscription.getFood().getHost().getMealRequestNotifications().size() - 1));
        }else throw new IllegalArgumentException("This is a public meal request");
    }



    public void refusePrivateMealRequest(ISubscription<IUser,IMealRequest> subscription) throws Exception {
        if(subscription.getFood().getType().equals(MealRequestType.PRIVATE)) {
            subscriptionManager.refusePrivateRequestNotification(this.logUser, subscription.getFood().getHost(), subscription, "ha rifiutato la tua richiesta privato di un pasto per il "
                    + subscription.getFood().getDate().toString() + " alle " + subscription.getFood().getTime().toString() );
            mealRequestPersistence.registerHomeOwnerToMealRequest(this.logUser, subscription.getFood(), subscription.getFood().getHost().getMealRequestNotifications().get(subscription.getFood().getHost().getMealRequestNotifications().size() - 1));
        }else throw new IllegalArgumentException("This is a public meal request");
    }


    /*public List<MealRequest> showPublishedMealRequests() throws Exception {
        List<MealRequest> mealsRequests = new ArrayList<>();
        for(MealRequest mealRequest: mealRequestPersistence.getMealsRequestList()){
            if(mealRequest.getHost().equals(this.logUser)) mealsRequests.add(mealRequest);
        }
        return mealsRequests;
    }

    public List<MealRequest> showAcceptedMealRequests() throws Exception {
        List<MealRequest> mealsRequests = new ArrayList<>();
        for(MealRequest mealRequest: mealRequestPersistence.getMealsRequestList()){
            if(mealRequest.getHomeOwner().equals(this.logUser)) mealsRequests.add(mealRequest);
        }
        return mealsRequests;
    }*/

    public IMealRequest getMealRequest(String id){
        return mealRequestPersistence.getMealsRequestMap().get(id);
    }


    public List<MealRequest> showPublishedMealRequests() throws Exception {
        return mealRequestPersistence.getMealsRequestList().stream().filter(p -> p.getHost().equals(this.logUser)).collect(Collectors.toList());
    }

    public List<MealRequest> showPublishedMealRequests(Predicate<MealRequest> predicate) throws Exception {
        return mealRequestPersistence.getMealsRequestList().stream().filter(p -> p.getHost().equals(this.logUser)).filter(predicate).collect(Collectors.toList());
    }

    public List<MealRequest> showAnsweredMealRequests() throws Exception {
        return mealRequestPersistence.getMealsRequestList().stream().filter(p -> p.getHomeOwner().equals(this.logUser)).collect(Collectors.toList());
    }

    public List<MealRequest> showAnsweredMealRequests(Predicate<MealRequest> predicate) throws Exception {
        return mealRequestPersistence.getMealsRequestList().stream().filter(p -> p.getHomeOwner().equals(this.logUser)).filter(predicate).collect(Collectors.toList());
    }


}
