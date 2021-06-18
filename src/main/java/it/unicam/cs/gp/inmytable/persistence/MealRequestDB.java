package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.allmeals.mealrequest.IMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequestType;
import it.unicam.cs.gp.inmytable.notification.SubscriptionNotification;
import it.unicam.cs.gp.inmytable.user.IUser;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MealRequestDB extends DBPersistence implements MealRequestPersistence{
    private String sql;



    public MealRequestDB() throws Exception {
        super();

    }

    public MealRequestDB(String connectionString, String username, String password) throws Exception {
        super(connectionString,username,password);

    }



    @Override
    public void registerPublicMealRequest(MealRequest mealRequest) throws SQLException {
        this.sql = "insert into MealRequest(Id, Host, HomeOwner, Type, Date, Time, ExpiryDate, ExpiryTime, Place, Price, MealsNumber, Allergy, MealType, Description, MealState, ConsummationType, PaymentType) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement prepStat = getConnection().prepareStatement(this.sql);
        prepStat.setString(1, mealRequest.getId());
        prepStat.setString(2, mealRequest.getHost().getUsername());
        prepStat.setNull(3,  19);
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
        getMealsRequestMap().put(mealRequest.getId(), mealRequest);
    }

    @Override
    public void registerPrivateMealRequest(MealRequest mealRequest, SubscriptionNotification<?,?> notification) throws SQLException {
        this.sql = "insert into MealRequest(Id, Host, HomeOwner, Type, Date, Time, ExpiryDate, ExpiryTime, Place, Price, MealsNumber, Allergy, MealType, Description, MealState, ConsummationType, PaymentType) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement prepStat = getConnection().prepareStatement(this.sql);
        prepStat.setString(1, mealRequest.getId());
        prepStat.setString(2, mealRequest.getHost().getUsername());
        prepStat.setString(3, mealRequest.getHomeOwner().getUsername());
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
        getMealsRequestMap().put(mealRequest.getId(), mealRequest);
        registerMealRequestSubscription(mealRequest.getHomeOwner(), mealRequest, notification);
        registerNotification(notification);
    }


    @Override
    public List<MealRequest> getMealsRequestList(){
        List<MealRequest> mealRequestList = new ArrayList<>();
        for(String key:getMealsRequestMap().keySet()){
            mealRequestList.add(getMealsRequestMap().get(key));
        }
        return mealRequestList;
    }


    @Override
    public List<MealRequest> getPublicMealsRequestList(){
        List<MealRequest> publicMealList = new ArrayList<>();
        for(String key:getMealsRequestMap().keySet()){
            if(getMealsRequestMap().get(key).getType().equals(MealRequestType.PUBLIC)){
                publicMealList.add(getMealsRequestMap().get(key));
            }
        }
        return publicMealList;
    }

    @Override
    public List<MealRequest> getPrivateMealsRequestList(){
        List<MealRequest> privateMealList = new ArrayList<>();
        for(String key:getMealsRequestMap().keySet()){
            if(getMealsRequestMap().get(key).getType().equals(MealRequestType.PRIVATE)){
                privateMealList.add(getMealsRequestMap().get(key));
            }
        }
        return privateMealList;
    }


    @Override
    public void registerHomeOwnerToMealRequest(IUser homeOwner, IMealRequest mealRequest, SubscriptionNotification<?,?> notification) throws SQLException {
        sql = "update MealRequest set HomeOwner=? where Id='"+mealRequest.getId()+"'";
        PreparedStatement prepStat = getConnection().prepareStatement(sql);
        prepStat.setString(1, mealRequest.getHomeOwner().getUsername());
        prepStat.executeUpdate();
        if(mealRequest.getType().equals(MealRequestType.PUBLIC)){
            registerMealRequestSubscription(homeOwner, mealRequest, notification);
        } else if(mealRequest.getType().equals(MealRequestType.PRIVATE)){
            updateMealRequestSubscription(mealRequest,notification);
        }
        updateMealRequest(mealRequest);
        registerNotification(notification);
    }



    private void registerMealRequestSubscription(IUser homeOwner, IMealRequest mealRequest,  SubscriptionNotification<?,?> notification) throws SQLException {
        sql = "insert into User_MealRequest(UserUsername, MealRequestId, SubscriptionState) values (?,?,?)";
        PreparedStatement prepStat = getConnection().prepareStatement(sql);
        prepStat.setString(1, homeOwner.getUsername());
        prepStat.setString(2, mealRequest.getId());
        prepStat.setString(3, notification.getSubscription().getState().toString());
        prepStat.executeUpdate();

    }


    private void updateMealRequestSubscription(IMealRequest mealRequest,  SubscriptionNotification<?,?> notification) throws SQLException {
        sql = "update User_MealRequest set SubscriptionState=? where MealRequestId='"+mealRequest.getId()+"'";
        PreparedStatement prepStat = getConnection().prepareStatement(sql);
        prepStat.setString(1, notification.getSubscription().getState().toString());
        prepStat.executeUpdate();

    }

    private void updateMealRequest(IMealRequest mealRequest) throws SQLException {
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
