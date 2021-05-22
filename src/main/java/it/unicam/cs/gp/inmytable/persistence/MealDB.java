package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.allmeals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.allmeals.PaymentType;
import it.unicam.cs.gp.inmytable.notification.INotification;
import it.unicam.cs.gp.inmytable.notification.SubscriptionNotification;
import it.unicam.cs.gp.inmytable.user.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealDB extends DBPersistence implements MealPersistence {
    private String sql;
   // private static Map<String, Meal> mealsMap;

  //  private static final String MEAL_SUBSCRIPTION_NOTIFICATION = "MealSubscriptionNotification";



    public MealDB() throws Exception {
        super();
       /* if(mealsMap==null) {
            mealsMap = new HashMap<>();
            fillMealMap();
        }*/
    }

    public MealDB(String connectionString, String username, String password) throws Exception {
        super(connectionString,username,password);
       /* if(mealsMap==null) {
            mealsMap = new HashMap<>();
            fillMealMap();
        }*/
    }

    @Override
    public void registerMeal(Meal meal) throws SQLException {
        this.sql = "insert into Meal(Id, HomeOwner, Date, Time, ExpiringDate, ExpiringTime, MealType, Place, ConsumationType, Description, Ingredients, MaxNumberUsers, Payment, Price, MealState, FreeSubscription) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement prepStat = getConnection().prepareStatement(this.sql);
        prepStat.setString(1, meal.getId());
        prepStat.setString(2, meal.getHomeOwner().getUsername());
        prepStat.setString(3, meal.getDate().toString());
        prepStat.setString(4, meal.getTime().toString());
        prepStat.setString(5, meal.getExpiryDate().toString());
        prepStat.setString(6, meal.getExpiryTime().toString());
        prepStat.setString(7, meal.getMealType());
        prepStat.setString(8, meal.getPlace());
        prepStat.setString(9, meal.getConsummationType().toString());
        prepStat.setString(10, meal.getDescription());
        prepStat.setString(11, meal.getIngredients());
        prepStat.setInt(12, meal.getMaxNumberUsers());
        prepStat.setString(13, meal.getPaymentType().toString());
        prepStat.setString(14, meal.getPrice());
        prepStat.setString(15, meal.getState().toString());
        prepStat.setBoolean(16, meal.isFreeSubscription());
        prepStat.executeUpdate();
        //mealsMap.put(meal.getId(), meal);
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



  /*  private void fillMealMap() throws Exception {
        String sql = "Select * from Meal";
        setData(sql);
        while (getData().next()) {
            if(! getMealsMap().containsKey(getData().getString("Id"))) {
                User homeOwner = getUsers().get(getData().getString("HomeOwner"));
                Meal meal = new Meal(homeOwner, getData().getInt("MaxNumberUsers"), LocalDate.parse(getData().getString("Date")), LocalTime.parse(getData().getString("Time")),
                        LocalDate.parse(getData().getString("ExpiringDate")), LocalTime.parse(getData().getString("ExpiringTime")), getData().getString("MealType"), getData().getBoolean("FreeSubscription"),
                        getData().getString("Place"), ConsumationType.valueOf(getData().getString("ConsumationType")), getData().getString("Description"), getData().getString("Ingredients"), PaymentType.valueOf(getData().getString("Payment")),
                        getData().getString("Price"));
                meal.setId(getData().getString("Id"));
                getMealsMap().put(meal.getId(), meal);
               updateMeal(meal);
            }
        }
        for (String k:  getMealsMap().keySet()){
            addUsersInMeal( getMealsMap().get(k));
        }
    }*/


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


    /*private Meal getMeal(String id) throws Exception {
        if(! getMealsMap().containsKey(id)) {
            fillMealMap();
        }
        return  getMealsMap().get(id);
    }


    private void addUsersInMeal(Meal meal) throws SQLException {
        String sql = "Select * from User_Meal where Mealid= '"+meal.getId()+"'";
        setData(sql);
        while (getData().next()) {
           User user = getUsers().get(getData().getString("Userusername"));
           meal.addUser(user);
        }
    }
*/
    private void updateMeal(IMeal meal) throws SQLException {
            sql = "update Meal set MealState=? where Id='"+meal.getId()+"'";
            PreparedStatement prepStat = getConnection().prepareStatement(sql);
            prepStat.setString(1, meal.getState().toString());
            prepStat.executeUpdate();
    }


}
