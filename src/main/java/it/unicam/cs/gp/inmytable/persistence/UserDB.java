package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.allmeals.meals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.allmeals.meals.MealStates;
import it.unicam.cs.gp.inmytable.allmeals.meals.PaymentType;
import it.unicam.cs.gp.inmytable.notification.Notification;
import it.unicam.cs.gp.inmytable.notification.NotificationStates;
import it.unicam.cs.gp.inmytable.notification.Subscription;
import it.unicam.cs.gp.inmytable.user.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDB extends DBConnection implements MealPersistence {
    private String sql;
    private Map<String, User> usersMap;
    private Map<Integer, Meal> mealsMap;

    public UserDB() throws Exception {
        super();
        usersMap = new HashMap<>();
        mealsMap = new HashMap<>();
        fillUsersMap();
        fillMealMap();
    }

    public UserDB(String connectionString, String username, String password) throws Exception {
        super(connectionString,username,password);
        usersMap = new HashMap<>();
        mealsMap = new HashMap<>();
        fillUsersMap();
        fillMealMap();
    }

    @Override
    public void registerMeal(Meal meal) throws SQLException {
        this.sql = "insert into Meal(Id, HomeOwner, Date, Time, ExpiringDate, ExpiringTime, MealType, Place, ConsumationType, Description, Ingredients, MaxNumberUsers, Payment, Price, MealState, FreeSubscription) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement prepStat = getConnection().prepareStatement(this.sql);
        prepStat.setInt(1, meal.hashCode());
        prepStat.setString(2, meal.getHomeOwner().getUsername());
        prepStat.setString(3, meal.getDate().toString());
        prepStat.setString(4, meal.getTime().toString());
        prepStat.setString(5, meal.getExpiryDate().toString());
        prepStat.setString(6, meal.getExpiryTime().toString());
        prepStat.setString(7, meal.getMealType());
        prepStat.setString(8, meal.getPlace());
        prepStat.setString(9, meal.getConsumationType().toString());
        prepStat.setString(10, meal.getDescription());
        prepStat.setString(11, meal.getIngredients());
        prepStat.setInt(12, meal.getMaxNumberUsers());
        prepStat.setString(13, meal.getPayment().toString());
        prepStat.setString(14, meal.getPrice());
        prepStat.setString(15, meal.getState().toString());
        prepStat.setBoolean(16, meal.isFreeSubscription());
        prepStat.executeUpdate();
    }

    @Override
    public void registerUserToMeal(User host, Meal meal, Subscription subscription) throws SQLException {
        this.sql = "insert into User_Meal(Userusername, Mealid, SubscriptionState) values (?,?,?)";
        PreparedStatement prepStat = getConnection().prepareStatement(this.sql);
        prepStat.setString(1, host.getUsername());
        prepStat.setInt(2, meal.hashCode());
        prepStat.setString(3, subscription.getNotificationState().toString());
        prepStat.executeUpdate();
    }

    @Override
    public List<Meal> getMealsList() throws Exception {
        fillMealMap();
        List<Meal> mealList = new ArrayList<>();
        for(int key:mealsMap.keySet()){
            mealList.add(mealsMap.get(key));
        }
        return mealList;
    }

    @Override
    public List<Notification> getNotificationsList() throws Exception {
        List<Notification> notificationsList = new ArrayList<>();
        String sql = "Select * from User_Meal";
        setData(sql);
        while (getData().next()) {
           User user = getUser(getData().getString("Userusername"));
           Meal meal = getMeal(getData().getInt("Mealid"));
           Subscription sub = new Subscription(user,meal);
           sub.setNotificationState(NotificationStates.valueOf(getData().getString("SubscriptionState")));
           notificationsList.add(sub);
        }
        return notificationsList;
    }

    private void fillUsersMap() throws SQLException {
        String sql = "Select * from User";
        setData(sql);
        while (getData().next()) {
            if(!usersMap.containsKey(getData().getString("Username"))){
                User user = new User(getData().getString("Username"), getData().getString("Email"), getData().getString("Telephone"),
                        getData().getString("FirstName"), getData().getString("LastName"), getData().getInt("Password"),
                        LocalDate.parse(getData().getString("Birth")), getData().getString("FiscalCode"), getData().getString("Id"),
                        getData().getString("Address"), getData().getBoolean("Available"));
                usersMap.put(user.getUsername(), user);
            }
        }
    }

    private void fillMealMap() throws Exception {
        String sql = "Select * from Meal";
        setData(sql);
        while (getData().next()) {
            if(!mealsMap.containsKey(Integer.parseInt(getData().getString("Id")))) {
                User homeOwner = getUser(getData().getString("HomeOwner"));
                Meal meal = new Meal(homeOwner, getData().getInt("MaxNumberUsers"), LocalDate.parse(getData().getString("Date")), LocalTime.parse(getData().getString("Time")),
                        LocalDate.parse(getData().getString("ExpiringDate")), LocalTime.parse(getData().getString("ExpiringTime")), getData().getString("MealType"), getData().getBoolean("FreeSubscription"),
                        getData().getString("Place"), ConsumationType.valueOf(getData().getString("ConsumationType")), getData().getString("Description"), getData().getString("Ingredients"), PaymentType.valueOf(getData().getString("Payment")),
                        getData().getString("Price"));
                // meal.setState(MealStates.valueOf(getData().getString("MealState")));
                addUsersInMeal(meal);
                mealsMap.put(meal.hashCode(), meal);
                updateMeal(meal);
                System.out.println(meal.getDescription());
            }
        }
    }

    private User getUser(String username) throws SQLException {
        if(!this.usersMap.containsKey(username)) {
            fillUsersMap();
        }
        return usersMap.get(username);
    }

    private Meal getMeal(int hashCode) throws Exception {
        if(!this.mealsMap.containsKey(hashCode)) {
            fillMealMap();
        }
        return mealsMap.get(hashCode);
    }


    private void addUsersInMeal(Meal meal) throws SQLException {
        String sql = "Select * from User_Meal where Mealid= '"+meal.hashCode()+"'";
        setData(sql);
        while (getData().next()) {
           User user = getUser(getData().getString("Userusername"));
           meal.addUser(user);
        }
    }

    private void updateMeal(Meal meal) throws SQLException {
            sql = "update Meal set MealState=? where Id='"+meal.hashCode()+"'";
            PreparedStatement prepStat = getConnection().prepareStatement(sql);
            prepStat.setString(1, meal.getState().toString());
            prepStat.executeUpdate();
    }


}
