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

    public void setLogUser(User logUser) throws Exception {
        this.me=logUser;
        this.mealsController = new MealsController(logUser);
        this.mealRequestsController = new MealRequestsController(logUser);
    }





    public List<INotification<IUser>> getAllNotifications(){
        List<INotification<IUser>> allNotifications = new ArrayList<>();
        allNotifications.addAll(me.getMealNotifications());
        allNotifications.addAll(me.getMealRequestNotifications());
        allNotifications.addAll(me.getSimpleNotifications());

        return sortedByDateTime(allNotifications);
    }



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



    public List<SubscriptionNotification<IUser, IMeal>> getMealNotifications(){
        return this.me.getMealNotifications();
    }

    public List<SubscriptionNotification<IUser, IMealRequest>> getMealRequestNotifications(){
        return this.me.getMealRequestNotifications();
    }

    public List<SimpleNotification<IUser>> getSimpleNotifications(){
        return this.me.getSimpleNotifications();
    }

    public SubscriptionNotification<IUser, IMeal> getMealSubscriptionNotifications(String id){
        for(SubscriptionNotification<IUser,IMeal> m:this.me.getMealNotifications()){
            if(m.getId().equals(id)) return m;
        }
        return null;
    }

    public SubscriptionNotification<IUser, IMealRequest> getMealRequestSubscriptionNotifications(String id){
        for(SubscriptionNotification<IUser,IMealRequest> m:this.me.getMealRequestNotifications()){
            if(m.getId().equals(id)) return m;
        }
        return null;
    }

    public SimpleNotification<IUser> getSimpleNotification(String id){
        for(SimpleNotification<IUser> m:this.me.getSimpleNotifications()){
            if(m.getId().equals(id)) return m;
        }
        return null;
    }

    public boolean isMealNotificationsWithPendingSubscription(String id){
        for(SubscriptionNotification<IUser,IMeal> m:this.me.getMealNotifications()){
            if(m.getId().equals(id) && m.getSubscription().getState().equals(SubscriptionStates.PENDING)){
                return true;
            }
        }
        return false;
    }

    public boolean isMealNotificationsWithNoPendingSubscription(String id){
        for(SubscriptionNotification<IUser,IMeal> m:this.me.getMealNotifications()){
            if(m.getId().equals(id) && !m.getSubscription().getState().equals(SubscriptionStates.PENDING)){
                return true;
            }
        }
        return false;
    }

    public boolean isMealRequestNotificationsWithPendingSubscription(String id){
        for(SubscriptionNotification<IUser,IMealRequest> m:this.me.getMealRequestNotifications()){
            if(m.getId().equals(id) && m.getSubscription().getState().equals(SubscriptionStates.PENDING)){
                return true;
            }
        }
        return false;
    }

    public boolean isMealRequestNotificationsWithNoPendingSubscription(String id){
        for(SubscriptionNotification<IUser,IMealRequest> m:this.me.getMealRequestNotifications()){
            if(m.getId().equals(id) && !m.getSubscription().getState().equals(SubscriptionStates.PENDING)){
                return true;
            }
        }
        return false;
    }

    public boolean isSimpleNotification(String id){
        for(SimpleNotification<IUser> m:this.me.getSimpleNotifications()){
            if(m.getId().equals(id)) return true;
        }
        return false;
    }

    public void acceptMealSubscriptionNotification(SubscriptionNotification<IUser, IMeal> notification) throws Exception {
        mealsController.acceptMealSubscription(notification.getSubscription());
    }

    public void refuseMealSubscriptionNotification(SubscriptionNotification<IUser, IMeal> notification) throws Exception {
        mealsController.refuseMealSubscription(notification.getSubscription());
    }

    public void acceptPrivateMealRequestSubscriptionNotification(SubscriptionNotification<IUser, IMealRequest> notification) throws Exception {
        mealRequestsController.acceptPrivateMealRequest(notification.getSubscription());
    }

    public void refusePrivateMealRequestSubscriptionNotification(SubscriptionNotification<IUser, IMealRequest> notification) throws Exception {
        mealRequestsController.refusePrivateMealRequest(notification.getSubscription());
    }

}
