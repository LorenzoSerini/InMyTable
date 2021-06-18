package it.unicam.cs.gp.inmytable.controllers;

import it.unicam.cs.gp.inmytable.allmeals.ConsummationType;
import it.unicam.cs.gp.inmytable.allmeals.MealManager;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.IMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequestType;
import it.unicam.cs.gp.inmytable.allmeals.PaymentType;
import it.unicam.cs.gp.inmytable.exception.TimeTravelException;
import it.unicam.cs.gp.inmytable.homewalls.HomeWall;
import it.unicam.cs.gp.inmytable.notification.ISubscription;
import it.unicam.cs.gp.inmytable.notification.SubscriptionManager;
import it.unicam.cs.gp.inmytable.persistence.*;
import it.unicam.cs.gp.inmytable.user.IUser;
import it.unicam.cs.gp.inmytable.user.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The meal request controller
 */
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

    /**
     * This method is used to post a public meal request.
     * @param description meal request description
     * @param mealType meal request type
     * @param consummationType the consummation type of the meal request
     * @param payment the meal request payment
     * @param date the meal request date
     * @param time the meal request time
     * @param expiryDate the meal request expiry date
     * @param expiryTime the meal request expiry time
     * @param price the meal request price
     * @param place the meal request place
     * @param allergy the host allergy
     * @param mealsNumber the meals number
     * @throws Exception
     */
    public void postPublicMealRequest(String description, String mealType, ConsummationType consummationType, PaymentType payment, String date, String time, String expiryDate, String expiryTime, String price, String place, String allergy, int mealsNumber) throws Exception {
        if (LocalDate.now().isAfter(LocalDate.parse(date)) || (LocalDate.now().isEqual(LocalDate.parse(date)) && LocalTime.now().isAfter(LocalTime.parse(time))))
            throw new TimeTravelException("date must be after " + LocalDate.now().toString() + " at " + LocalTime.now().toString());
        MealRequest publicMealRequest = mealManager.createPublicMealRequest(logUser, mealType, consummationType, payment, description, LocalDate.parse(date), LocalTime.parse(time),
                LocalDate.parse(expiryDate), LocalTime.parse(expiryTime), price, place, allergy, mealsNumber );
        mealRequestPersistence.registerPublicMealRequest(publicMealRequest);
    }

    /**
     * This method is used to post a private meal request.
     * @param homeOwner the user to ask for the meal request
     * @param description meal request description
     * @param mealType meal request type
     * @param consummationType the consummation type of the meal request
     * @param payment the meal request payment
     * @param date the meal request date
     * @param time the meal request time
     * @param expiryDate the meal request expiry date
     * @param expiryTime the meal request expiry time
     * @param price the meal request price
     * @param place the meal request place
     * @param allergy the host allergy
     * @param mealsNumber the meals number
     * @throws Exception
     */
    public void postPrivateMealRequest(User homeOwner, String description, String mealType, ConsummationType consummationType, PaymentType payment, String date, String time, String expiryDate, String expiryTime, String price, String place, String allergy, int mealsNumber) throws Exception {
        if (LocalDate.now().isAfter(LocalDate.parse(date)) || (LocalDate.now().isEqual(LocalDate.parse(date)) && LocalTime.now().isAfter(LocalTime.parse(time))))
            throw new TimeTravelException("date must be after " + LocalDate.now().toString() + " at " + LocalTime.now().toString());
        MealRequest privateMealRequest = mealManager.createPrivateMealRequest(logUser, mealType, consummationType, payment, description, LocalDate.parse(date), LocalTime.parse(time),
                LocalDate.parse(expiryDate), LocalTime.parse(expiryTime), price, place, allergy, mealsNumber, homeOwner );
        subscriptionManager.sendPrivateRequestNotification(this.logUser, homeOwner, privateMealRequest, "ti ha richiesto un pasto per il " + privateMealRequest.getDate().toString() + " alle " + privateMealRequest.getTime().toString());
        mealRequestPersistence.registerPrivateMealRequest(privateMealRequest, homeOwner.getMealRequestNotifications().get(homeOwner.getMealRequestNotifications().size()-1));
    }


    /**
     * This method is used to accept a public or private meal request
     * @param mealRequest the meal request to be accepted
     * @throws Exception
     */
    public void acceptPublicMealRequest(MealRequest mealRequest) throws Exception {
        if(mealRequest.getType().equals(MealRequestType.PUBLIC)) {
            mealRequest.setHomeOwner(this.logUser);
            subscriptionManager.acceptPublicRequestNotification(this.logUser, mealRequest.getHost(), mealRequest, "ha accettato la tua richiesta di pasto pubblico che si terrà il " + mealRequest.getDate().toString() + " alle " + mealRequest.getTime().toString());
            mealRequestPersistence.registerHomeOwnerToMealRequest(this.logUser, mealRequest, mealRequest.getHost().getMealRequestNotifications().get(mealRequest.getHost().getMealRequestNotifications().size() - 1));
        }else throw new IllegalArgumentException("This is a private meal request");
    }


    /**
     * This method is used to accept a private meal request
     * @param subscription the meal request subscription created when the user made the request
     * @throws Exception
     */
    public void acceptPrivateMealRequest(ISubscription<IUser,IMealRequest> subscription) throws Exception {
        if(subscription.getFood().getType().equals(MealRequestType.PRIVATE)) {
            subscriptionManager.acceptPrivateRequestNotification(this.logUser, subscription.getFood().getHost(), subscription, "ha accettato la tua richiesta privato di un pasto per il "
                    + subscription.getFood().getDate().toString() + " alle " + subscription.getFood().getTime().toString() );
            mealRequestPersistence.registerHomeOwnerToMealRequest(this.logUser, subscription.getFood(), subscription.getFood().getHost().getMealRequestNotifications().get(subscription.getFood().getHost().getMealRequestNotifications().size() - 1));
        }else throw new IllegalArgumentException("This is a public meal request");
    }


    /**
     * This method is used to refuse a private meal request
     * @param subscription the meal request subscription created when the user made the request
     * @throws Exception
     */
    public void refusePrivateMealRequest(ISubscription<IUser,IMealRequest> subscription) throws Exception {
        if(subscription.getFood().getType().equals(MealRequestType.PRIVATE)) {
            subscriptionManager.refusePrivateRequestNotification(this.logUser, subscription.getFood().getHost(), subscription, "ha rifiutato la tua richiesta privato di un pasto per il "
                    + subscription.getFood().getDate().toString() + " alle " + subscription.getFood().getTime().toString() );
            mealRequestPersistence.registerHomeOwnerToMealRequest(this.logUser, subscription.getFood(), subscription.getFood().getHost().getMealRequestNotifications().get(subscription.getFood().getHost().getMealRequestNotifications().size() - 1));
        }else throw new IllegalArgumentException("This is a public meal request");
    }


    /**
     * returns meal request by id
     * @param id the meal request id
     * @return meal request
     */
    public IMealRequest getMealRequest(String id){
        return mealRequestPersistence.getMealsRequestMap().get(id);
    }

    /**
     * returns all meal requests published by this user
      * @return the requests published by this user
     * @throws Exception
     */
    public List<MealRequest> showPublishedMealRequests() throws Exception {
        return mealRequestPersistence.getMealsRequestList().stream().filter(p->p.getHost()!=null).filter(p -> p.getHost().equals(this.logUser)).collect(Collectors.toList());
    }

    /**
     * returns meal requests published by this user
     * @param predicate filter by predicate
     * @return meal requests published by this user
     * @throws Exception
     */
    public List<MealRequest> showPublishedMealRequests(Predicate<MealRequest> predicate) throws Exception {
        return this.showPublishedMealRequests().stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * returns all meal requests answered by this user
     * @return all meal requests answered by this user
     * @throws Exception
     */
    public List<MealRequest> showAnsweredMealRequests() throws Exception {
        return mealRequestPersistence.getMealsRequestList().stream().filter(p->p.getHomeOwner()!=null).filter(p -> p.getHomeOwner().equals(this.logUser)).collect(Collectors.toList());
    }

    /**
     * returns meal requests answered by this user
     * @param predicate filter by predicate
     * @return meal requests answered by this user
     * @throws Exception
     */
    public List<MealRequest> showAnsweredMealRequests(Predicate<MealRequest> predicate) throws Exception {
        return this.showAnsweredMealRequests().stream().filter(predicate).collect(Collectors.toList());
    }


}
