package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest.PrivateMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest.PublicMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.meals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.PaymentType;
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

public class MealRequestDB extends DBPersistence implements MealRequestPersistence{
    private String sql;
    private static final String PUBLIC = "PUBLIC";
    private static final String PRIVATE = "PRIVATE";
    private Map<Integer, PublicMealRequest> publicMealsRequestMap;
    private Map<Integer, PrivateMealRequest> privateMealsRequestMap;

    public MealRequestDB() throws Exception {
        super();
        publicMealsRequestMap = new HashMap<>();
        privateMealsRequestMap = new HashMap<>();
        fillMealRequestMap();
    }

    public MealRequestDB(String connectionString, String username, String password) throws Exception {
        super(connectionString,username,password);
        publicMealsRequestMap = new HashMap<>();
        privateMealsRequestMap = new HashMap<>();
        fillMealRequestMap();
    }


    @Override
    public void registerMealRequest(PublicMealRequest mealRequest, NotificationStates notificationStates) throws SQLException {
        this.sql = "insert into MealRequest(Id, Host, HomeOwner, Type, Date, Time, ExpiryDate, ExpiryTime, Place, Price, MealsNumber, Allergy, MealType, Description, MealState, ConsummationType, PaymentType, SubscriptionState) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement prepStat = getConnection().prepareStatement(this.sql);
        prepStat.setInt(1, mealRequest.hashCode());
        prepStat.setString(2, mealRequest.getHost().getUsername());
        if(mealRequest.getHomeOwner()!=null) {
            prepStat.setString(3, mealRequest.getHomeOwner().getUsername());
        }else  prepStat.setNull(3,  19);
        prepStat.setString(4, PUBLIC);
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
        prepStat.setString(18, notificationStates.toString());
        prepStat.executeUpdate();
        this.publicMealsRequestMap.put(mealRequest.hashCode(), mealRequest);
    }


    @Override
    public void registerMealRequest(PrivateMealRequest mealRequest, NotificationStates notificationStates) throws SQLException {
        this.sql = "insert into MealRequest(Id, Host, HomeOwner, Type, Date, Time, ExpiryDate, ExpiryTime, Place, Price, MealsNumber, Allergy, MealType, Description, MealState, ConsummationType, PaymentType, SubscriptionState) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement prepStat = getConnection().prepareStatement(this.sql);
        prepStat.setInt(1, mealRequest.hashCode());
        prepStat.setString(2, mealRequest.getHost().getUsername());
        prepStat.setString(3, mealRequest.getHomeOwner().getUsername());
        prepStat.setString(4, PRIVATE);
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
        prepStat.setString(18, notificationStates.toString());
        prepStat.executeUpdate();
        this.privateMealsRequestMap.put(mealRequest.hashCode(), mealRequest);
    }



    @Override
    public List<PublicMealRequest> getPublicMealsRequestMap() throws Exception {
        fillMealRequestMap();
        List<PublicMealRequest> publicMealList = new ArrayList<>();
        for(int key:this.publicMealsRequestMap.keySet()){
            publicMealList.add(publicMealsRequestMap.get(key));
        }
        return publicMealList;
    }

    @Override
    public List<PrivateMealRequest> getPrivateMealsRequestMap() throws Exception {
        fillMealRequestMap();
        List<PrivateMealRequest> privateMealList = new ArrayList<>();
        for(int key:this.publicMealsRequestMap.keySet()){
            privateMealList.add(privateMealsRequestMap.get(key));
        }
        return privateMealList;
    }


    @Override
    public void registerHomeOwnerToMealRequest(User homeOwner, MealRequest mealRequest, Subscription subscription) throws SQLException {
        sql = "update MealRequest set HomeOwner=? and SubscriptionState=? where Id='"+mealRequest.hashCode()+"'";
        PreparedStatement prepStat = getConnection().prepareStatement(sql);
        prepStat.setString(1, homeOwner.getUsername());
        prepStat.setString(2, subscription.getNotificationState().toString());
        prepStat.executeUpdate();
    }


    private void fillMealRequestMap() throws Exception {
        String sql = "Select * from MealRequest";
        setData(sql);
        while (getData().next()) {
            if(!publicMealsRequestMap.containsKey(getData().getInt("Id")) && !privateMealsRequestMap.containsKey(getData().getInt("Id"))) {
                User host = getUsers().get(getData().getString("Host"));
                User homeOwner = getUsers().get(getData().getString("HomeOwner"));
                switch (getData().getString("Type")){
                    case PUBLIC:
                       PublicMealRequest pubMealRequest = new PublicMealRequest(host, getData().getString("MealType"), ConsumationType.valueOf(getData().getString("ConsummationType")),
                                PaymentType.valueOf(getData().getString("PaymentType")), getData().getString("Description"), LocalDate.parse(getData().getString("Date")),
                                LocalTime.parse(getData().getString("Time")), LocalDate.parse(getData().getString("ExpiryDate")), LocalTime.parse(getData().getString("ExpiryTime")),
                                getData().getString("Price"), getData().getString("Place"), getData().getString("Allergy"), getData().getInt("MealsNumber"));
                        pubMealRequest.setHomeOwner(homeOwner);
                        publicMealsRequestMap.put(Integer.parseInt(getData().getString("Id")), pubMealRequest);
                        updateMealRequest(pubMealRequest);

                        case PRIVATE:
                        PrivateMealRequest priMealRequest = new PrivateMealRequest(host, getData().getString("MealType"), ConsumationType.valueOf(getData().getString("ConsummationType")),
                                PaymentType.valueOf(getData().getString("PaymentType")), getData().getString("Description"), LocalDate.parse(getData().getString("Date")),
                                LocalTime.parse(getData().getString("Time")), LocalDate.parse(getData().getString("ExpiryDate")), LocalTime.parse(getData().getString("ExpiryTime")),
                                getData().getString("Price"), getData().getString("Place"), getData().getString("Allergy"), getData().getInt("MealsNumber"), homeOwner);
                        privateMealsRequestMap.put(Integer.parseInt(getData().getString("Id")), priMealRequest);
                            updateMealRequest(priMealRequest);
                }
            }
        }
    }


    private void updateMealRequest(MealRequest mealRequest) throws SQLException {
        sql = "update MealRequest set MealState=? where Id='"+mealRequest.hashCode()+"'";
        PreparedStatement prepStat = getConnection().prepareStatement(sql);
        prepStat.setString(1, mealRequest.getState().toString());
        prepStat.executeUpdate();
    }



}
