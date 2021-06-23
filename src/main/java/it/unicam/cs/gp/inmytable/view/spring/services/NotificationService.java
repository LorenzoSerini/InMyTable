package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.allmeals.mealrequest.IMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
import it.unicam.cs.gp.inmytable.controllers.MealRequestsController;
import it.unicam.cs.gp.inmytable.controllers.MealsController;
import it.unicam.cs.gp.inmytable.notification.*;
import it.unicam.cs.gp.inmytable.user.IUser;
import it.unicam.cs.gp.inmytable.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private MealsController mealsController;
    private MealRequestsController mealRequestsController;
    private User me;

    /**
     * used to set log user
     * @param logUser the log user
     * @throws Exception
     */
    public void setLogUser(User logUser) throws Exception {
        this.me=logUser;
        this.mealsController = new MealsController(logUser);
        this.mealRequestsController = new MealRequestsController(logUser);
    }


    /**
     * returns all notification
     * @return all notifications
     */
    public List<INotification<IUser>> getAllNotifications(){
        List<INotification<IUser>> allNotifications = new ArrayList<>();
        allNotifications.addAll(me.getMealNotifications());
        allNotifications.addAll(me.getMealRequestNotifications());
        allNotifications.addAll(me.getSimpleNotifications());

        return sortedByDateTime(allNotifications);
    }


    /**
     * returns notifications list sorted by date
     * @param notifications notifications list
     * @return notifications list sorted by date
     */
    public List<INotification<IUser>> sortedByDateTime(List<INotification<IUser>> notifications){
        for(int x=0; x<notifications.size()-1;x++) {
            for (int y = x+1; y < notifications.size(); y++) {
                if ((notifications.get(x).getDate().isBefore(notifications.get(y).getDate()))||(notifications.get(x).getDate().isEqual(notifications.get(y).getDate()) &&
                        notifications.get(x).getTime().isBefore(notifications.get(y).getTime()))) {
                    Collections.swap(notifications, x, y);
                }
            }
        }
        return notifications;
    }


    /**
     * returns meal notifications
     * @return meal notifications
     */
    public List<SubscriptionNotification<IUser, IMeal>> getMealNotifications(){
        return this.me.getMealNotifications();
    }

    /**
     * returns meal requests notifications
     * @return meal requests notifications
     */
    public List<SubscriptionNotification<IUser, IMealRequest>> getMealRequestNotifications(){
        return this.me.getMealRequestNotifications();
    }

    /**
     * returns simple notifications
     * @return simple notifications
     */
    public List<SimpleNotification<IUser>> getSimpleNotifications(){
        return this.me.getSimpleNotifications();
    }

    /**
     * returns meal subscription notifications
     * @param id meal subscription notifications id
     * @return meal subscription notifications
     */
    public SubscriptionNotification<IUser, IMeal> getMealSubscriptionNotifications(String id){
        for(SubscriptionNotification<IUser,IMeal> m:this.me.getMealNotifications()){
            if(m.getId().equals(id)) return m;
        }
        return null;
    }

    /**
     * returns meal requests subscription notifications
     * @param id meal requests subscription notifications id
     * @return meal requests subscription notifications
     */
    public SubscriptionNotification<IUser, IMealRequest> getMealRequestSubscriptionNotifications(String id){
        for(SubscriptionNotification<IUser,IMealRequest> m:this.me.getMealRequestNotifications()){
            if(m.getId().equals(id)) return m;
        }
        return null;
    }

    /**
     * returns simple notifications
     * @param id simple notifications id
     * @return simple notifications
     */
    public SimpleNotification<IUser> getSimpleNotification(String id){
        for(SimpleNotification<IUser> m:this.me.getSimpleNotifications()){
            if(m.getId().equals(id)) return m;
        }
        return null;
    }

    /**
     * if is meal notification with pending subscription return true else false
     * @param id meal notification with pending subscription id
     * @return meal notification with pending subscription
     */
    public boolean isMealNotificationsWithPendingSubscription(String id){
        for(SubscriptionNotification<IUser,IMeal> m:this.me.getMealNotifications()){
            if(m.getId().equals(id) && m.getSubscription().getState().equals(SubscriptionStates.PENDING)){
                return true;
            }
        }
        return false;
    }

    /**
     * if is meal notification with not pending subscription return true else false
     * @param id meal notification with not pending subscription id
     * @return meal notification with not pending subscription
     */
    public boolean isMealNotificationsWithNoPendingSubscription(String id){
        for(SubscriptionNotification<IUser,IMeal> m:this.me.getMealNotifications()){
            if(m.getId().equals(id) && !m.getSubscription().getState().equals(SubscriptionStates.PENDING)){
                return true;
            }
        }
        return false;
    }


    /**
     * if is meal request notification with pending subscription return true else false
     * @param id meal request notification with pending subscription id
     * @return meal request notification with pending subscription
     */
    public boolean isMealRequestNotificationsWithPendingSubscription(String id){
        for(SubscriptionNotification<IUser,IMealRequest> m:this.me.getMealRequestNotifications()){
            if(m.getId().equals(id) && m.getSubscription().getState().equals(SubscriptionStates.PENDING)){
                return true;
            }
        }
        return false;
    }

    /**
     * if is meal request notification with not pending subscription return true else false
     * @param id meal request notification with not pending subscription id
     * @return meal request notification with not pending subscription
     */
    public boolean isMealRequestNotificationsWithNoPendingSubscription(String id){
        for(SubscriptionNotification<IUser,IMealRequest> m:this.me.getMealRequestNotifications()){
            if(m.getId().equals(id) && !m.getSubscription().getState().equals(SubscriptionStates.PENDING)){
                return true;
            }
        }
        return false;
    }

    /**
     * if is simple notification return true else false
     * @param id simple notifications id
     * @return is simple notification return true else false
     */
    public boolean isSimpleNotification(String id){
        for(SimpleNotification<IUser> m:this.me.getSimpleNotifications()){
            if(m.getId().equals(id)) return true;
        }
        return false;
    }

    /**
     * this method is used to accept meal subscription notification
     * @param notification the notification
     * @throws Exception
     */
    public void acceptMealSubscriptionNotification(SubscriptionNotification<IUser, IMeal> notification) throws Exception {
        mealsController.acceptMealSubscription(notification.getSubscription());
    }

    /**
     * this method is used to refuse meal subscription notification
     * @param notification the notification
     * @throws Exception
     */
    public void refuseMealSubscriptionNotification(SubscriptionNotification<IUser, IMeal> notification) throws Exception {
        mealsController.refuseMealSubscription(notification.getSubscription());
    }

    /**
     * this method is used to accept private meal request subscription notification
     * @param notification the notification
     * @throws Exception
     */
    public void acceptPrivateMealRequestSubscriptionNotification(SubscriptionNotification<IUser, IMealRequest> notification) throws Exception {
        mealRequestsController.acceptPrivateMealRequest(notification.getSubscription());
    }

    /**
     * this method is used to refuse private meal request subscription notification
     * @param notification the notification
     * @throws Exception
     */
    public void refusePrivateMealRequestSubscriptionNotification(SubscriptionNotification<IUser, IMealRequest> notification) throws Exception {
        mealRequestsController.refusePrivateMealRequest(notification.getSubscription());
    }

}
