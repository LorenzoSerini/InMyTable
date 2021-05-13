package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequestType;
import it.unicam.cs.gp.inmytable.allmeals.meals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.MealStates;
import it.unicam.cs.gp.inmytable.allmeals.meals.PaymentType;
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

//TODO: QUANDO UNA RICHIESTA DI PASTO SUPERA LA DATA DI SCADENZA NON VIENE AGGIORNATO SUBITO NEL DB, DA MODIFICARE LA REQUESTPERSISTENCE!!

public class MealRequestDB extends DBPersistence implements MealRequestPersistence{
    private String sql;
    private static Map<String, MealRequest> mealsRequestMap;


    private static final String PRIVATE = "PRIVATE";
    private static final String PUBLIC = "PUBLIC";


    private static final String MEAL_REQUEST_SUBSCRIPTION_NOTIFICATION = "MealRequestSubscriptionNotification";


    public MealRequestDB() throws Exception {
        super();
        if(mealsRequestMap==null){
            mealsRequestMap = new HashMap<>();
            fillMealRequestMap();
        }

    }

    public MealRequestDB(String connectionString, String username, String password) throws Exception {
        super(connectionString,username,password);
        if(mealsRequestMap==null){
            mealsRequestMap = new HashMap<>();
            fillMealRequestMap();
        }

    }



    @Override
    public void registerMealRequest(MealRequest mealRequest) throws SQLException {
        this.sql = "insert into MealRequest(Id, Host, HomeOwner, Type, Date, Time, ExpiryDate, ExpiryTime, Place, Price, MealsNumber, Allergy, MealType, Description, MealState, ConsummationType, PaymentType) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement prepStat = getConnection().prepareStatement(this.sql);
        prepStat.setString(1, mealRequest.getId());
        prepStat.setString(2, mealRequest.getHost().getUsername());
        if(mealRequest.getHomeOwner()!=null){
            prepStat.setString(3, mealRequest.getHomeOwner().getUsername());
        }else  prepStat.setNull(3,  19);
        prepStat.setString(4, mealRequest.getType().toString());
        prepStat.setString(5, mealRequest.getDate().toString());
        prepStat.setString(6, mealRequest.getTime().toString());
        prepStat.setString(7, mealRequest.getExpiryDate().toString());
        prepStat.setString(8, mealRequest.getExpiryTime().toString());
        prepStat.setString(9, mealRequest.getPlace());
        prepStat.setString(10, mealRequest.getPrice());
        prepStat.setInt(11, mealRequest.getMealsNumber());
        prepStat.setString(12, mealRequest.getAllergy());
        prepStat.setString(13, mealRequest.getMealType());
        prepStat.setString(14, mealRequest.getDescription());
        prepStat.setString(15, mealRequest.getState().toString());
        prepStat.setString(16, mealRequest.getConsummationType().toString());
        prepStat.setString(17, mealRequest.getPaymentType().toString());
        prepStat.executeUpdate();
        mealsRequestMap.put(mealRequest.getId(), mealRequest);
    }


    @Override
    public List<MealRequest> getMealsRequestMap(){
        List<MealRequest> mealRequestList = new ArrayList<>();
        for(String key:mealsRequestMap.keySet()){
            mealRequestList.add(mealsRequestMap.get(key));
        }
        return mealRequestList;
    }


    @Override
    public List<MealRequest> getPublicMealsRequestMap(){
        List<MealRequest> publicMealList = new ArrayList<>();
        for(String key:mealsRequestMap.keySet()){
            if(mealsRequestMap.get(key).getType().equals(MealRequestType.PUBLIC)){
                publicMealList.add(mealsRequestMap.get(key));
            }
        }
        return publicMealList;
    }

    @Override
    public List<MealRequest> getPrivateMealsRequestMap(){
        List<MealRequest> privateMealList = new ArrayList<>();
        for(String key:mealsRequestMap.keySet()){
            if(mealsRequestMap.get(key).getType().equals(MealRequestType.PRIVATE)){
                privateMealList.add(mealsRequestMap.get(key));
            }
        }
        return privateMealList;
    }


    @Override
    public void registerHomeOwnerToMealRequest(User homeOwner, MealRequest mealRequest,  SubscriptionNotification<?,?> notification) throws SQLException {
        sql = "insert into User_MealRequest(UserUsername, MealRequestId, SubscriptionState) values (?,?,?)";
        PreparedStatement prepStat = getConnection().prepareStatement(sql);
        prepStat.setString(1, homeOwner.getUsername());
        prepStat.setString(2, mealRequest.getId());
        prepStat.setString(3, notification.getSubscription().getState().toString());
        prepStat.executeUpdate();
        updateMealRequest(mealRequest);
        registerNotification(notification);
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
                    pubMealRequest.setState(MealStates.valueOf(getData().getString("MealState")));
                    if (homeOwner != null) pubMealRequest.setHomeOwner(homeOwner);
                    mealsRequestMap.put(pubMealRequest.getId(), pubMealRequest);
                    updateMealRequest(pubMealRequest);
                    System.out.println(pubMealRequest.getType().toString());
                    System.out.println(getMealsRequestMap().get(0).getType().toString());
                }else if(getData().getString("Type").equals(PRIVATE)){
                    MealRequest priMealRequest = new MealRequest(host, getData().getString("MealType"), ConsumationType.valueOf(getData().getString("ConsummationType")),
                            PaymentType.valueOf(getData().getString("PaymentType")), getData().getString("Description"), LocalDate.parse(getData().getString("Date")),
                            LocalTime.parse(getData().getString("Time")), LocalDate.parse(getData().getString("ExpiryDate")), LocalTime.parse(getData().getString("ExpiryTime")),
                            getData().getString("Price"), getData().getString("Place"), getData().getString("Allergy"), getData().getInt("MealsNumber"), homeOwner);
                    priMealRequest.setId(getData().getString("Id"));
                    priMealRequest.setState(MealStates.valueOf(getData().getString("MealState")));
                    mealsRequestMap.put(priMealRequest.getId(),priMealRequest);
                    updateMealRequest(priMealRequest);
                }
                }
            }
        }



    private void updateMealRequest(MealRequest mealRequest) throws SQLException {
        sql = "update MealRequest set MealState=? where Id='"+mealRequest.getId()+"'";
        PreparedStatement prepStat = getConnection().prepareStatement(sql);
        prepStat.setString(1, mealRequest.getState().toString());
        prepStat.executeUpdate();
    }


    private void registerNotification(SubscriptionNotification<?,?> notification) throws SQLException {
        this.sql = "insert into Notification(Id, Date, Time, FromUser, ToUser, Type, FoodId, Message) values (?,?,?,?,?,?,?,?)";
        PreparedStatement prepStat = getConnection().prepareStatement(this.sql);
        prepStat.setString(1, notification.getId());
        prepStat.setString(2, notification.getDate().toString());
        prepStat.setString(3, notification.getTime().toString());
        prepStat.setString(4, notification.from().getUsername());
        prepStat.setString(5, notification.to().getUsername());
        prepStat.setString(6, MEAL_REQUEST_SUBSCRIPTION_NOTIFICATION);
        prepStat.setString(7, notification.getSubscription().getFood().getId());
        prepStat.setString(8, notification.getMsg());
        prepStat.executeUpdate();
    }



}
