package it.unicam.cs.gp.inmytable.persistence;


import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.notification.SubscriptionNotification;
import it.unicam.cs.gp.inmytable.user.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MealDB extends DBPersistence implements MealPersistence {
    private String sql;



    public MealDB() throws Exception {
        super();
    }

    public MealDB(String connectionString, String username, String password) throws Exception {
        super(connectionString,username,password);
    }

    @Override
    public void registerMeal(Meal meal) throws SQLException {
        this.sql = "insert into Meal(Id, HomeOwner, Date, Time, ExpiringDate, ExpiringTime, MealType, City, Place, ConsumationType, Description, Ingredients, MaxNumberUsers, Payment, Price, MealState, FreeSubscription) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement prepStat = getConnection().prepareStatement(this.sql);
        prepStat.setString(1, meal.getId());
        prepStat.setString(2, meal.getHomeOwner().getUsername());
        prepStat.setString(3, meal.getDate().toString());
        prepStat.setString(4, meal.getTime().toString());
        prepStat.setString(5, meal.getExpiryDate().toString());
        prepStat.setString(6, meal.getExpiryTime().toString());
        prepStat.setString(7, meal.getMealType());
        prepStat.setString(8, meal.getCity());
        prepStat.setString(9, meal.getPlace());
        prepStat.setString(10, meal.getConsummationType().toString());
        prepStat.setString(11, meal.getDescription());
        prepStat.setString(12, meal.getIngredients());
        prepStat.setInt(13, meal.getMaxNumberUsers());
        prepStat.setString(14, meal.getPaymentType().toString());
        prepStat.setString(15, meal.getPrice());
        prepStat.setString(16, meal.getState().toString());
        prepStat.setBoolean(17, meal.isFreeSubscription());
        prepStat.executeUpdate();
        getMealsMap().put(meal.getId(), meal);
    }

    @Override
    public void registerUserToMeal(User host, Meal meal, SubscriptionNotification<?,?> notification) throws SQLException {
        this.sql = "insert into User_Meal(Userusername, Mealid, SubscriptionState) values (?,?,?)";
        PreparedStatement prepStat = getConnection().prepareStatement(this.sql);
        prepStat.setString(1, host.getUsername());
        prepStat.setString(2, meal.getId());
        prepStat.setString(3, notification.getSubscription().getState().toString());
        prepStat.executeUpdate();
        registerNotification(notification);
    }


    @Override
    public void acceptOrRefuseMealSubscription(IMeal meal, SubscriptionNotification<?,?> notification) throws SQLException {
        sql = "update User_Meal set SubscriptionState=? where MealId='"+notification.getSubscription().getFood().getId()+"'";
        PreparedStatement prepStat = getConnection().prepareStatement(sql);
        prepStat.setString(1, notification.getSubscription().getState().toString());
        prepStat.executeUpdate();
        updateMeal(meal);
        registerNotification(notification);
    }

    @Override
    public List<Meal> getMealsList(){
        List<Meal> mealList = new ArrayList<>();
        for(String key: getMealsMap().keySet()){
            mealList.add(getMealsMap().get(key));
        }
        return mealList;
    }





    private void registerNotification(SubscriptionNotification<?,?> notification) throws SQLException {
        this.sql = "insert into Notification(Id, Date, Time, FromUser, ToUser, Type, FoodId, Message) values (?,?,?,?,?,?,?,?)";
        PreparedStatement prepStat = getConnection().prepareStatement(this.sql);
        prepStat.setString(1, notification.getId());
        prepStat.setString(2, notification.getDate().toString());
        prepStat.setString(3, notification.getTime().toString());
        prepStat.setString(4, notification.from().getUsername());
        prepStat.setString(5, notification.to().getUsername());
        prepStat.setString(6, MEAL_SUBSCRIPTION_NOTIFICATION);
        prepStat.setString(7, notification.getSubscription().getFood().getId());
        prepStat.setString(8, notification.getMsg());
        prepStat.executeUpdate();
    }


    private void updateMeal(IMeal meal) throws SQLException {
            sql = "update Meal set MealState=? where Id='"+meal.getId()+"'";
            PreparedStatement prepStat = getConnection().prepareStatement(sql);
            prepStat.setString(1, meal.getState().toString());
            prepStat.executeUpdate();
    }


}
