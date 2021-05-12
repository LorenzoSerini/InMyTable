package it.unicam.cs.gp.inmytable.notification;

import it.unicam.cs.gp.inmytable.allmeals.mealrequest.IMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequestType;
import it.unicam.cs.gp.inmytable.allmeals.meals.Food;
import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
import it.unicam.cs.gp.inmytable.allmeals.meals.MealStates;
import it.unicam.cs.gp.inmytable.user.IUser;

public class SubscriptionManager{


    public <T extends IUser, M extends IMeal> void joinToMealNotification(T from, T to, M meal, String msg){
        if(meal.getState().equals(MealStates.PENDING)) {
            MealSubscription<IUser, IMeal> mealSubscription = new MealSubscription<>(from, meal);
            if(meal.isFreeSubscription()) mealSubscription.accept();
            SubscriptionNotification<IUser, IMeal> notification = new SubscriptionNotification<>(from, meal.getHomeOwner(), mealSubscription, msg);
            //meal.getHomeOwner().getMealNotifications().add(notification);
            to.getMealNotifications().add(notification);
        }else throw new IllegalArgumentException("You cannot sign up for his meal");
    }


    public <T extends IUser> void acceptMealNotification(T from, T to, ISubscription<IUser,IMeal> subscription, String msg){
        if(subscription.getFood().isFreeSubscription() && subscription.getFood().getPlacesAvailable()!=0){
            subscription.accept();
            SubscriptionNotification<IUser, IMeal> notification = new SubscriptionNotification<>(from, to, subscription, msg);
            to.getMealNotifications().add(notification);
        }
    }



    public <T extends IUser, M extends IMealRequest> void acceptPublicRequestNotification(T from, T to, M mealRequest, String msg){
        if(mealRequest.getState().equals(MealStates.PENDING)){
            mealRequest.setState(MealStates.FULL);
            MealRequestSubscription<IUser, IMealRequest> mealRequestSubscription = new MealRequestSubscription<>(from, mealRequest);
            mealRequestSubscription.accept();
            SubscriptionNotification<IUser, IMealRequest> notification = new SubscriptionNotification<>(from, to, mealRequestSubscription, msg);//"L'utente " + this.user.getUsername() + " ha accettato la tua richiesta di pasto pubblico che si terrà il " + mealRequest.getDate().toString() + " alle " + mealRequest.getTime().toString());
            mealRequest.getHost().getMealRequestNotifications().add(notification);
        }
    }


    public <T extends IUser, M extends IMealRequest> void sendPrivateRequestNotification(T from, T to, M mealRequest, String msg){
        if(mealRequest.getType().equals(MealRequestType.PRIVATE)){
            MealRequestSubscription<IUser, IMealRequest> mealRequestSubscription = new MealRequestSubscription<>(from, mealRequest);
            SubscriptionNotification<IUser, IMealRequest> notification = new SubscriptionNotification<>(from, to, mealRequestSubscription, msg);//"L'utente " + this.user.getUsername() + " ha accettato la tua richiesta di pasto pubblico che si terrà il " + mealRequest.getDate().toString() + " alle " + mealRequest.getTime().toString());
            //mealRequest.getHost().getMealRequestNotifications().add(notification);
            to.getMealRequestNotifications().add(notification);
        }
    }


    public <T extends IUser> void acceptPrivateRequestNotification(T from, T to, ISubscription<IUser,IMealRequest> subscription, String msg){
        if(subscription.getState().equals(SubscriptionStates.PENDING)){
            subscription.accept();
            subscription.getFood().setState(MealStates.FULL);
            SubscriptionNotification<IUser, IMealRequest> notification = new SubscriptionNotification<>(from, to, subscription, msg);
            to.getMealRequestNotifications().add(notification);
        }
    }



    public <T extends IUser> void refusePrivateRequestNotification(T from, T to, ISubscription<IUser,IMealRequest> subscription, String msg){
        if(subscription.getFood().getType().equals(MealRequestType.PRIVATE) && subscription.getState().equals(SubscriptionStates.PENDING)) {
            subscription.refuse();
            subscription.getFood().setState(MealStates.FULL);
            SubscriptionNotification<IUser, IMealRequest> notification = new SubscriptionNotification<>(from, to, subscription, msg);
            to.getMealRequestNotifications().add(notification);
        }
    }


}
