package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequestType;
import it.unicam.cs.gp.inmytable.allmeals.meals.ConsumationType;
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
    private Map<Integer, MealRequest> mealsRequestMap;
    private Map<Integer, MealRequest> publicMealsRequestMap;
    private Map<Integer, MealRequest> privateMealsRequestMap;

    private static final String PRIVATE = "PRIVATE";
    private static final String PUBLIC = "PUBLIC";


    private static final String SUBSCRIPTION_NOTIFICATION = "SubscriptionNotification";


    public MealRequestDB() throws Exception {
        super();
        mealsRequestMap = new HashMap<>();
        publicMealsRequestMap = new HashMap<>();
        privateMealsRequestMap = new HashMap<>();
        fillMealRequestMap();
    }

    public MealRequestDB(String connectionString, String username, String password) throws Exception {
        super(connectionString,username,password);
        mealsRequestMap = new HashMap<>();
        publicMealsRequestMap = new HashMap<>();
        privateMealsRequestMap = new HashMap<>();
        fillMealRequestMap();
    }



    @Override
    public void registerMealRequest(MealRequest mealRequest) throws SQLException {
        this.sql = "insert into MealRequest(Id, Host, HomeOwner, Type, Date, Time, ExpiryDate, ExpiryTime, Place, Price, MealsNumber, Allergy, MealType, Description, MealState, ConsummationType, PaymentType) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement prepStat = getConnection().prepareStatement(this.sql);
        prepStat.setInt(1, mealRequest.hashCode());
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
        saveInMealRequestMaps(mealRequest);
    }

    private void saveInMealRequestMaps(MealRequest mealRequest){
        mealsRequestMap.put(mealRequest.hashCode(), mealRequest);
        if(mealRequest.getType().equals(MealRequestType.PUBLIC)){
            publicMealsRequestMap.put(mealRequest.hashCode(), mealRequest);
        }else if (mealRequest.getType().equals(MealRequestType.PRIVATE)){
            privateMealsRequestMap.put(mealRequest.hashCode(), mealRequest);
        }
    }


    @Override
    public List<MealRequest> getMealsRequestMap() throws Exception {
        fillMealRequestMap();
        List<MealRequest> mealRequestList = new ArrayList<>();
        for(int key:this.mealsRequestMap.keySet()){
            mealRequestList.add(mealsRequestMap.get(key));
        }
        return mealRequestList;
    }


    @Override
    public List<MealRequest> getPublicMealsRequestMap() throws Exception {
        fillMealRequestMap();
        List<MealRequest> publicMealList = new ArrayList<>();
        for(int key:this.publicMealsRequestMap.keySet()){
            publicMealList.add(publicMealsRequestMap.get(key));
        }
        return publicMealList;
    }

    @Override
    public List<MealRequest> getPrivateMealsRequestMap() throws Exception {
        fillMealRequestMap();
        List<MealRequest> privateMealList = new ArrayList<>();
        for(int key:this.publicMealsRequestMap.keySet()){
            privateMealList.add(privateMealsRequestMap.get(key));
        }
        return privateMealList;
    }


    @Override
    public void registerHomeOwnerToMealRequest(User homeOwner, MealRequest mealRequest,  SubscriptionNotification<?,?> notification) throws SQLException {
       /* SE NON FACCIO L'UPDATE IMMETTENDO L'HOMEOWNER NELLA TABELLA MealRequest RIESCO A MANTENERE UNA DIFFERENZA
       TRA RICHIESTA PASTO PUBBLICHE (L'HOMEOWNER NON C'E' MA STARà SOLO NELLA TABELLA USER_MEALREQUEST E RICHIESTE
       DI PASTO PRIVATE DOVE L'HOMEOWNER STARA' IN TUTTE E DUE LE TABELLE!!
       sql = "update MealRequest set HomeOwner=? where Id='"+mealRequest.hashCode()+"'";
        PreparedStatement prepStat = getConnection().prepareStatement(sql);
        prepStat.setString(1, homeOwner.getUsername());
        prepStat.setString(2, subscription.getNotificationState().toString());
        prepStat.executeUpdate();
         registerNotification(notification);
        */
        sql = "insert into User_MealRequest(UserUsername, MealRequestId, SubscriptionState) values (?,?,?)";
        PreparedStatement prepStat = getConnection().prepareStatement(sql);
        prepStat.setString(1, homeOwner.getUsername());
        prepStat.setInt(2, mealRequest.hashCode());
        prepStat.setString(3, notification.getSubscription().getState().toString());
        prepStat.executeUpdate();
        registerNotification(notification);
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
                        MealRequest pubMealRequest = new MealRequest(host, getData().getString("MealType"), ConsumationType.valueOf(getData().getString("ConsummationType")),
                                PaymentType.valueOf(getData().getString("PaymentType")), getData().getString("Description"), LocalDate.parse(getData().getString("Date")),
                                LocalTime.parse(getData().getString("Time")), LocalDate.parse(getData().getString("ExpiryDate")), LocalTime.parse(getData().getString("ExpiryTime")),
                                getData().getString("Price"), getData().getString("Place"), getData().getString("Allergy"), getData().getInt("MealsNumber"));
                        if(homeOwner!=null)pubMealRequest.setHomeOwner(homeOwner);
                        saveInMealRequestMaps(pubMealRequest);
                        updateMealRequest(pubMealRequest);
                        case PRIVATE:
                           MealRequest priMealRequest = new MealRequest(host, getData().getString("MealType"), ConsumationType.valueOf(getData().getString("ConsummationType")),
                                PaymentType.valueOf(getData().getString("PaymentType")), getData().getString("Description"), LocalDate.parse(getData().getString("Date")),
                                LocalTime.parse(getData().getString("Time")), LocalDate.parse(getData().getString("ExpiryDate")), LocalTime.parse(getData().getString("ExpiryTime")),
                                getData().getString("Price"), getData().getString("Place"), getData().getString("Allergy"), getData().getInt("MealsNumber"), homeOwner);
                            saveInMealRequestMaps(priMealRequest);
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


    private void registerNotification(SubscriptionNotification<?,?> notification) throws SQLException {
        this.sql = "insert into Notification(Id, Date, Time, FromUser, ToUser, Type, FoodId, Message) values (?,?,?,?,?,?,?,?)";
        PreparedStatement prepStat = getConnection().prepareStatement(this.sql);
        prepStat.setInt(1, notification.hashCode());
        prepStat.setString(2, notification.getDate().toString());
        prepStat.setString(3, notification.getTime().toString());
        prepStat.setString(4, notification.from().getUsername());
        prepStat.setString(5, notification.to().getUsername());
        prepStat.setString(6, SUBSCRIPTION_NOTIFICATION);
        prepStat.setInt(7, notification.getSubscription().getFood().hashCode());
        prepStat.setString(8, notification.getMsg());
        prepStat.executeUpdate();
    }



}
