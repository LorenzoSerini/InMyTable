package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.allmeals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.MealStates;
import it.unicam.cs.gp.inmytable.allmeals.PaymentType;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.allmeals.meals.*;
import it.unicam.cs.gp.inmytable.user.IUser;
import it.unicam.cs.gp.inmytable.user.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public abstract class DBPersistence extends DBConnection implements Persistence{
    private String sql;
    private static Map<String, User> usersMap;
    private static Map<String, Meal> mealsMap;
    private static Map<String, MealRequest> mealsRequestMap;

    public static final String MEAL_SUBSCRIPTION_NOTIFICATION = "MealSubscriptionNotification";
    public static final String MEAL_REQUEST_SUBSCRIPTION_NOTIFICATION = "MealRequestSubscriptionNotification";
    public static final String PRIVATE = "PRIVATE";
    public static final String PUBLIC = "PUBLIC";


    public DBPersistence() throws Exception {
        super();
        if(usersMap==null||mealsMap==null||mealsRequestMap==null) {
            usersMap = new HashMap<>();
            mealsMap = new HashMap<>();
            mealsRequestMap = new HashMap<>();
            fillUsersMap();
            fillMealMap();
            fillMealRequestMap();
        }
    }

    public DBPersistence(String connectionString, String username, String password) throws Exception {
        super(connectionString,username,password);
        if(usersMap==null||mealsMap==null||mealsRequestMap==null) {
            usersMap = new HashMap<>();
            mealsMap = new HashMap<>();
            mealsRequestMap = new HashMap<>();
            fillUsersMap();
            fillMealMap();
            fillMealRequestMap();
        }
    }

    @Override
    public Map<String, User> getUsers(){
        return usersMap;
    }

    @Override
    public Map<String, Meal> getMealsMap(){
        return mealsMap;
    }

    @Override
    public Map<String, MealRequest> getMealsRequestMap(){
        return mealsRequestMap;
    }

    private Map<String, User> fillUsersMap() throws Exception {
        sql = "Select * from User";
        setData(sql);
        while (getData().next()) {
            if(!usersMap.containsKey(getData().getString("Username"))){
                User user = new User(getData().getString("Username"), getData().getString("Email"), getData().getString("Telephone"),
                        getData().getString("FirstName"), getData().getString("LastName"), getData().getInt("Password"),
                        LocalDate.parse(getData().getString("Birth")), getData().getString("FiscalCode"), getData().getString("Id"),
                        getData().getString("City"), getData().getString("Address"), getData().getBoolean("Available"));
                usersMap.put(user.getUsername(), user);
            }
        }
        return usersMap;
    }


    private void fillMealMap() throws Exception {
        String sql = "Select * from Meal";
        setData(sql);
        Meal meal = null;
        while (getData().next()) {
            if(! getMealsMap().containsKey(getData().getString("Id"))) {
                User homeOwner = getUsers().get(getData().getString("HomeOwner"));
                 meal = new Meal(homeOwner, getData().getInt("MaxNumberUsers"), LocalDate.parse(getData().getString("Date")), LocalTime.parse(getData().getString("Time")),
                        LocalDate.parse(getData().getString("ExpiringDate")), LocalTime.parse(getData().getString("ExpiringTime")), getData().getString("MealType"), getData().getBoolean("FreeSubscription"),
                        getData().getString("Place"), ConsumationType.valueOf(getData().getString("ConsumationType")), getData().getString("Description"), getData().getString("Ingredients"), PaymentType.valueOf(getData().getString("Payment")),
                        getData().getString("Price"));
                meal.setId(getData().getString("Id"));
                getMealsMap().put(meal.getId(), meal);
               // updateMeal(meal);
            }
        }
        for (String k:  getMealsMap().keySet()){
            if(mealsMap.get(k).getState().equals(MealStates.EXPIRED)){
                addUsersInMeal(getMealsMap().get(k));
                mealsMap.get(k).setState(MealStates.EXPIRED);
            } else addUsersInMeal(getMealsMap().get(k));
        }
        if(meal!=null) updateMeal(meal);
    }


    private void fillMealRequestMap() throws Exception {
        String sql = "Select * from MealRequest";
        setData(sql);
        while (getData().next()) {
            if(!mealsRequestMap.containsKey(getData().getString("Id"))){
                User host = getUsers().get(getData().getString("Host"));
                User homeOwner = getUsers().get(getData().getString("HomeOwner"));
                if(getData().getString("Type").equals(PUBLIC)) {
                    MealRequest pubMealRequest = new MealRequest(host, getData().getString("MealType"), ConsumationType.valueOf(getData().getString("ConsummationType")),
                            PaymentType.valueOf(getData().getString("PaymentType")), getData().getString("Description"), LocalDate.parse(getData().getString("Date")),
                            LocalTime.parse(getData().getString("Time")), LocalDate.parse(getData().getString("ExpiryDate")), LocalTime.parse(getData().getString("ExpiryTime")),
                            getData().getString("Price"), getData().getString("Place"), getData().getString("Allergy"), getData().getInt("MealsNumber"));
                    pubMealRequest.setId(getData().getString("Id"));
                    if(pubMealRequest.getState().equals(MealStates.PENDING)) pubMealRequest.setState(MealStates.valueOf(getData().getString("MealState")));
                    if (homeOwner != null) pubMealRequest.setHomeOwner(homeOwner);
                    mealsRequestMap.put(pubMealRequest.getId(), pubMealRequest);
                    updateMealRequest(pubMealRequest);
                }else if(getData().getString("Type").equals(PRIVATE)){
                    MealRequest priMealRequest = new MealRequest(host, getData().getString("MealType"), ConsumationType.valueOf(getData().getString("ConsummationType")),
                            PaymentType.valueOf(getData().getString("PaymentType")), getData().getString("Description"), LocalDate.parse(getData().getString("Date")),
                            LocalTime.parse(getData().getString("Time")), LocalDate.parse(getData().getString("ExpiryDate")), LocalTime.parse(getData().getString("ExpiryTime")),
                            getData().getString("Price"), getData().getString("Place"), getData().getString("Allergy"), getData().getInt("MealsNumber"), homeOwner);
                    priMealRequest.setId(getData().getString("Id"));
                    if(priMealRequest.getState().equals(MealStates.PENDING)) priMealRequest.setState(MealStates.valueOf(getData().getString("MealState")));
                    mealsRequestMap.put(priMealRequest.getId(),priMealRequest);
                    updateMealRequest(priMealRequest);
                }
            }
        }
    }




    private void addUsersInMeal(Meal meal) throws SQLException {
        String sql = "Select * from User_Meal where Mealid= '"+meal.getId()+"' and SubscriptionState <> 'PENDING' and SubscriptionState <> 'REFUSED' ";
        setData(sql);
        while (getData().next()) {
            User user = getUsers().get(getData().getString("Userusername"));
            meal.addUser(user);
        }
    }

    private void updateMeal(Meal meal) throws SQLException {
        sql = "update Meal set MealState=? where Id='"+meal.getId()+"'";
        PreparedStatement prepStat = getConnection().prepareStatement(sql);
        prepStat.setString(1, meal.getState().toString());
        prepStat.executeUpdate();
    }


    private void updateMealRequest(MealRequest mealRequest) throws SQLException {
        sql = "update MealRequest set MealState=? where Id='"+mealRequest.getId()+"'";
        PreparedStatement prepStat = getConnection().prepareStatement(sql);
        prepStat.setString(1, mealRequest.getState().toString());
        prepStat.executeUpdate();
    }


}
