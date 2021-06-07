package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.allmeals.Food;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.IMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
import it.unicam.cs.gp.inmytable.notification.*;
import it.unicam.cs.gp.inmytable.user.IUser;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationDB extends DBPersistence implements NotificationPersistence{
    private String sql;
    private static Map<String, SubscriptionNotification<IUser, IMeal>> mealSubscriptionNotificationsMap;
    private static Map<String, SubscriptionNotification<IUser, IMealRequest>> mealsRequestSubscriptionNotificationsMap;
    private static Map<String, SimpleNotification<IUser>> simpleNotificationsMap;




    public NotificationDB() throws Exception {
        super();
        if(mealSubscriptionNotificationsMap==null||mealsRequestSubscriptionNotificationsMap==null||simpleNotificationsMap==null){
           mealSubscriptionNotificationsMap = new HashMap<>();
           mealsRequestSubscriptionNotificationsMap = new HashMap<>();
           simpleNotificationsMap = new HashMap<>();
           fillMealSubscriptionNotificationMap();
           fillMealRequestSubscriptionNotificationMap();
           fillSimpleNotificationMap();
           fillAllUsersNotificationsLists();
        }
    }

    public NotificationDB(String connectionString, String username, String password) throws Exception {
        super(connectionString,username,password);
        if(mealSubscriptionNotificationsMap==null||mealsRequestSubscriptionNotificationsMap==null||simpleNotificationsMap==null){
            mealSubscriptionNotificationsMap = new HashMap<>();
            mealsRequestSubscriptionNotificationsMap = new HashMap<>();
            simpleNotificationsMap = new HashMap<>();
            fillMealSubscriptionNotificationMap();
            fillMealRequestSubscriptionNotificationMap();
            fillSimpleNotificationMap();
            fillAllUsersNotificationsLists();
        }
    }



    @Override
    public List<SubscriptionNotification<IUser, Food>> getSubscriptionNotifications() {
        return null;
    }

    @Override
    public List<SubscriptionNotification<IUser, IMealRequest>> getMealRequestNotifications(IUser user) {
        List<SubscriptionNotification<IUser, IMealRequest>> mealRequestNotificationsList = new ArrayList<>();
        for (String key : mealsRequestSubscriptionNotificationsMap.keySet()) {
            if (mealsRequestSubscriptionNotificationsMap.get(key).to().equals(user)) {
                mealRequestNotificationsList.add(mealsRequestSubscriptionNotificationsMap.get(key));
            }
        }
        return mealRequestNotificationsList;
    }


    @Override
    public List<SubscriptionNotification<IUser, IMeal>> getMealNotifications(IUser user) {
        List<SubscriptionNotification<IUser, IMeal>> mealNotificationsList = new ArrayList<>();
        for(String key:mealSubscriptionNotificationsMap.keySet()){
            if(mealSubscriptionNotificationsMap.get(key).to().equals(user)){
                mealNotificationsList.add(mealSubscriptionNotificationsMap.get(key));
            }
        }
        return mealNotificationsList;
    }

    @Override
    public List<SimpleNotification<IUser>> getSimpleNotifications(IUser user) {
        List<SimpleNotification<IUser>> simpleNotificationsList = new ArrayList<>();
        for (String key : simpleNotificationsMap.keySet()) {
            if (simpleNotificationsMap.get(key).to().equals(user)) {
                simpleNotificationsList.add(simpleNotificationsMap.get(key));
            }
        }
        return simpleNotificationsList;
    }

    @Override
    public void registerSimpleNotification(SimpleNotification<?> notification) throws SQLException {
        this.sql = "insert into Notification(Id, Date, Time, FromUser, ToUser, Type, Message) values (?,?,?,?,?,?,?)";
        PreparedStatement prepStat = getConnection().prepareStatement(this.sql);
        prepStat.setString(1, notification.getId());
        prepStat.setString(2, notification.getDate().toString());
        prepStat.setString(3, notification.getTime().toString());
        prepStat.setString(4, notification.from().getUsername());
        prepStat.setString(5, notification.to().getUsername());
        prepStat.setString(6, SIMPLE_NOTIFICATION);
        prepStat.setString(7, notification.getMsg());
        prepStat.executeUpdate();
    }

    private void fillMealSubscriptionNotificationMap() throws SQLException {
        String sql = "Select * from Notification where Type='"+MEAL_SUBSCRIPTION_NOTIFICATION+"'";
        setData(sql);
        while (getData().next()) {
            if(!mealSubscriptionNotificationsMap.containsKey(getData().getString("Id"))) {
                MealSubscription<IUser, IMeal> mealSubscription = new MealSubscription<>(getUsers().get(getData().getString("FromUser")), getMealsMap().get(getData().getString("FoodId")));
                SubscriptionNotification<IUser, IMeal> notification = new SubscriptionNotification<>(getUsers().get(getData().getString("FromUser")),getUsers().get(getData().getString("ToUser")), mealSubscription, getData().getString("Message"));
                notification.setId(getData().getString("Id"));
                notification.setDate(LocalDate.parse(getData().getString("Date")));
                notification.setTime(LocalTime.parse(getData().getString("Time")));
                mealSubscriptionNotificationsMap.put(notification.getId(), notification);
            }
        }
        for (String key: mealSubscriptionNotificationsMap.keySet()){
            setMealSubscriptionState(mealSubscriptionNotificationsMap.get(key));
        }
    }


    private void fillMealRequestSubscriptionNotificationMap() throws SQLException {
        String sql = "Select * from Notification where Type='"+MEAL_REQUEST_SUBSCRIPTION_NOTIFICATION+"'";
        setData(sql);
        while (getData().next()) {
            if(!mealsRequestSubscriptionNotificationsMap.containsKey(getData().getString("Id"))) {
                MealRequestSubscription<IUser, IMealRequest> mealRequestSubscription = new MealRequestSubscription<IUser, IMealRequest>(getUsers().get(getData().getString("FromUser")), getMealsRequestMap().get(getData().getString("FoodId")));
                SubscriptionNotification<IUser, IMealRequest> notification = new SubscriptionNotification<>(getUsers().get(getData().getString("FromUser")),getUsers().get(getData().getString("ToUser")), mealRequestSubscription, getData().getString("Message"));
                notification.setId(getData().getString("Id"));
                notification.setDate(LocalDate.parse(getData().getString("Date")));
                notification.setTime(LocalTime.parse(getData().getString("Time")));
                mealsRequestSubscriptionNotificationsMap.put(notification.getId(), notification);
            }
        }
        for (String key: mealsRequestSubscriptionNotificationsMap.keySet()){
            setMealRequestSubscriptionState(mealsRequestSubscriptionNotificationsMap.get(key));
        }
    }


    private void fillSimpleNotificationMap() throws SQLException {
        String sql = "Select * from Notification where Type='"+SIMPLE_NOTIFICATION+"'";
        setData(sql);
        while (getData().next()) {
            if(!simpleNotificationsMap.containsKey(getData().getString("Id"))) {
                SimpleNotification<IUser> notification = new SimpleNotification<>(getUsers().get(getData().getString("FromUser")),getUsers().get(getData().getString("ToUser")), getData().getString("Message"));
                notification.setId(getData().getString("Id"));
                notification.setDate(LocalDate.parse(getData().getString("Date")));
                notification.setTime(LocalTime.parse(getData().getString("Time")));
                simpleNotificationsMap.put(notification.getId(), notification);
            }
        }
    }



    private void setMealSubscriptionState( SubscriptionNotification<IUser, IMeal> notification) throws SQLException {
        String sql = "Select * from User_Meal where Mealid='"+notification.getSubscription().getFood().getId()+"'";
        setData(sql);
        while (getData().next()) {
           notification.getSubscription().setState(SubscriptionStates.valueOf(getData().getString("SubscriptionState")));
        }
    }

    private void setMealRequestSubscriptionState( SubscriptionNotification<IUser, IMealRequest> notification) throws SQLException {
        String sql = "Select * from User_MealRequest where MealRequestId='"+notification.getSubscription().getFood().getId()+"'";
        setData(sql);
        while (getData().next()) {
            notification.getSubscription().setState(SubscriptionStates.valueOf(getData().getString("SubscriptionState")));
        }
    }


    private void fillAllUsersNotificationsLists(){
        for(String key:getUsers().keySet()){
             getUsers().get(key).getMealNotifications().addAll(getMealNotifications(getUsers().get(key)));
             getUsers().get(key).getMealRequestNotifications().addAll(getMealRequestNotifications(getUsers().get(key)));
            getUsers().get(key).getSimpleNotifications().addAll(getSimpleNotifications(getUsers().get(key)));
        }
    }

}
