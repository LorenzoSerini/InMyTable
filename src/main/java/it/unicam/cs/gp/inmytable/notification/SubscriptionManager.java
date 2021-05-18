package it.unicam.cs.gp.inmytable.notification;

import it.unicam.cs.gp.inmytable.allmeals.MealStates;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.IMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequestType;
import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;

import it.unicam.cs.gp.inmytable.user.IUser;

public class SubscriptionManager{


    /**
     * Create a new notification with sender from and recipient to with inside a subscription
     * notification created by the user from who subscribes to the meal
     * @param from the user who subscribes to the meal
     * @param to the user who receives the notification (the homeOwner of the meal)
     * @param meal the meal the from user signs up for
     * @param msg the notification message
     * @param <T> IUser
     * @param <M> IMeal
     */
    public <T extends IUser, M extends IMeal> void joinToMealNotification(T from, T to, M meal, String msg){
        if(from.equals(to)) throw new IllegalArgumentException("You cannot join to this meal!");
        if(meal.getState().equals(MealStates.PENDING)) {
            MealSubscription<IUser, IMeal> mealSubscription = new MealSubscription<>(from, meal);
            if(meal.isFreeSubscription()) mealSubscription.accept();
            SubscriptionNotification<IUser, IMeal> notification = new SubscriptionNotification<>(from, meal.getHomeOwner(), mealSubscription, msg);
            to.getMealNotifications().add(notification);
        }else throw new IllegalArgumentException("You cannot sign up for his meal");
    }

    /**
     * is used to accept a meal subscription and create a new notification that notifies
     * the user that the subscription has been accepted by the user from
     * @param from The user that accept the subscription
     * @param to The user who signed up for the meal
     * @param subscription The meal subscription
     * @param msg The notification message
     * @param <T> IUser
     */
    public <T extends IUser> void acceptMealNotification(T from, T to, ISubscription<IUser,IMeal> subscription, String msg){
        if (from.equals(to) || subscription.getUser().equals(from)) throw new IllegalArgumentException("You cannot accept!");
        if (subscription.getState() != SubscriptionStates.PENDING) throw new IllegalArgumentException("You cannot accept this!");
        if(!subscription.getFood().isFreeSubscription() && subscription.getFood().getPlacesAvailable()!=0){
            subscription.accept();
            SubscriptionNotification<IUser, IMeal> notification = new SubscriptionNotification<>(from, to, subscription, msg);
            to.getMealNotifications().add(notification);
        }
    }


    public <T extends IUser> void refuseMealNotification(T from, T to, ISubscription<IUser,IMeal> subscription, String msg){
        if (from.equals(to) || subscription.getUser().equals(from)) throw new IllegalArgumentException("You cannot accept!");
        if (subscription.getState() != SubscriptionStates.PENDING) throw new IllegalArgumentException("You cannot refuse this!");
        if(!subscription.getFood().isFreeSubscription() && subscription.getFood().getPlacesAvailable()!=0){
            subscription.refuse();
            SubscriptionNotification<IUser, IMeal> notification = new SubscriptionNotification<>(from, to, subscription, msg);
            to.getMealNotifications().add(notification);
        }
    }

    /**
     * is used to accept a public meal request and create a new notification that notifies
     * the user that the public meal request has been accepted by the user from
     * @param from The user that accept the request
     * @param to The user that post the meal request
     * @param mealRequest The posted meal request
     * @param msg The notification message
     * @param <T> IUser
     * @param <M> IMealRequest
     */
    public <T extends IUser, M extends IMealRequest> void acceptPublicRequestNotification(T from, T to, M mealRequest, String msg){
        if (from.equals(to)) throw new IllegalArgumentException("You cannot accept this!");
        if(mealRequest.getState()==MealStates.PENDING){
            mealRequest.setState(MealStates.FULL);
            MealRequestSubscription<IUser, IMealRequest> mealRequestSubscription = new MealRequestSubscription<>(from, mealRequest);
            mealRequestSubscription.accept();
            SubscriptionNotification<IUser, IMealRequest> notification = new SubscriptionNotification<>(from, to, mealRequestSubscription, msg);//"L'utente " + this.user.getUsername() + " ha accettato la tua richiesta di pasto pubblico che si terrà il " + mealRequest.getDate().toString() + " alle " + mealRequest.getTime().toString());
            to.getMealRequestNotifications().add(notification);
        }
    }



    /**
     * is used to send a private meal request and create a new notification with inside a subscription
     * notification that notifies the user that the user from has been request a private meal
     * @param from The user that send the request
     * @param to The user that received the private meal request
     * @param mealRequest The sent meal request
     * @param msg The notification message
     * @param <T> IUser
     * @param <M> IMealRequest
     */
    public <T extends IUser, M extends IMealRequest> void sendPrivateRequestNotification(T from, T to, M mealRequest, String msg){
        if (from.equals(to)) throw new IllegalArgumentException("You cannot send this!");
        if(mealRequest.getType().equals(MealRequestType.PRIVATE)){
            MealRequestSubscription<IUser, IMealRequest> mealRequestSubscription = new MealRequestSubscription<>(from, mealRequest);
            SubscriptionNotification<IUser, IMealRequest> notification = new SubscriptionNotification<>(from, to, mealRequestSubscription, msg);//"L'utente " + this.user.getUsername() + " ha accettato la tua richiesta di pasto pubblico che si terrà il " + mealRequest.getDate().toString() + " alle " + mealRequest.getTime().toString());
            to.getMealRequestNotifications().add(notification);
        }
    }

    /**
     * is used to accept a private meal request and create a new notification that notifies
     * the user that the private meal request has been accepted by the user from
     * @param from The user that accept the request
     * @param to The user that send the request
     * @param subscription The private meal request subscription
     * @param msg The notification message
     * @param <T> IUser
     */
    public <T extends IUser> void acceptPrivateRequestNotification(T from, T to, ISubscription<IUser,IMealRequest> subscription, String msg){
        if (from.equals(to)) throw new IllegalArgumentException("You cannot accept this request!");
        if(subscription.getState().equals(SubscriptionStates.PENDING)){
            subscription.accept();
            subscription.getFood().setState(MealStates.FULL);
            SubscriptionNotification<IUser, IMealRequest> notification = new SubscriptionNotification<>(from, to, subscription, msg);
            to.getMealRequestNotifications().add(notification);
        }
    }


    /**
     * is used to refuse a private meal request and create a new notification that notifies
     * the user that the private meal request has been refused by the user from
     * @param from The user that refuse the request
     * @param to The user that send the request
     * @param subscription The private meal request subscription
     * @param msg The notification message
     * @param <T> IUser
     */
    public <T extends IUser> void refusePrivateRequestNotification(T from, T to, ISubscription<IUser,IMealRequest> subscription, String msg){
        if (from.equals(to)) throw new IllegalArgumentException("You cannot accept this request!");
        if(subscription.getFood().getType().equals(MealRequestType.PRIVATE) && subscription.getState().equals(SubscriptionStates.PENDING)) {
            subscription.refuse();
            subscription.getFood().setState(MealStates.FULL);
            SubscriptionNotification<IUser, IMealRequest> notification = new SubscriptionNotification<>(from, to, subscription, msg);
            to.getMealRequestNotifications().add(notification);
        }
    }


}
