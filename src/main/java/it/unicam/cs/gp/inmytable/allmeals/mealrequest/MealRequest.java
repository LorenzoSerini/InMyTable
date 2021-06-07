package it.unicam.cs.gp.inmytable.allmeals.mealrequest;

import it.unicam.cs.gp.inmytable.allmeals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.MealStates;
import it.unicam.cs.gp.inmytable.allmeals.PaymentType;

import it.unicam.cs.gp.inmytable.user.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;


/**
 * MealRequest from an host to one homeOwner or more.
 */
public class MealRequest implements IMealRequest {

    private LocalDate date;
    private LocalTime time;
    private LocalDate expiryDate;
    private LocalTime expiryTime;
    private String place;
    private String price;
    private int mealsNumber;
    private String allergy;
    private String mealType;
    private String description;
    private IUser host;
    private IUser homeOwner;
    private MealStates state;
    private ConsumationType consummationType;
    private PaymentType paymentType;
    private MealRequestType type;
    private String id;

    /**
     * Build a public Meal Request
     * @param host host who request a meal
     * @param mealType  type of the meal
     * @param consumationType   type of consummation
     * @param payment   type of payment
     * @param description description of the meal request
     * @param date  date of the meal request
     * @param time  time of the meal request
     * @param expiryDate    expiration date
     * @param expiryTime    expiration time
     * @param price price
     * @param place place
     * @param allergy   allergy
     * @param mealsNumber   number of the meal
     * @throws Exception    if one of the parameters is null or if the expiration date is after the date
     */
    public MealRequest(IUser host, String mealType, ConsumationType consumationType, PaymentType payment, String description,
                       LocalDate date, LocalTime time, LocalDate expiryDate, LocalTime expiryTime, String price,
                       String place, String allergy, int mealsNumber) throws Exception {
        if ((host == null) || (mealType == null) || (consumationType == null) || (payment == null) || (description == null) ||
                (date == null) || time == null || expiryDate == null || expiryTime == null || price == null || place == null
                || allergy ==null) throw new NullPointerException("Parameters must be different to null|");
        if (payment.compareTo(PaymentType.FREE) == 0) {
            this.price = "0";
        } else this.price = price;
        this.host = host;
        this.mealType = mealType;
        this.consummationType = consumationType;
        this.paymentType = payment;
        this.description = description;
        this.date = date;
        this.time = time;
        this.expiryDate = expiryDate;
        this.expiryTime = expiryTime;
        this.place = place;
        this.allergy = allergy;
        this.mealsNumber = mealsNumber;
        if (LocalDate.now().isAfter(date) || (LocalDate.now().isEqual(date) && LocalTime.now().isAfter(time) )) {
            this.state= MealStates.EXPIRED;
        } else if (LocalDate.now().isAfter(expiryDate) || (LocalDate.now().isEqual(expiryDate) && LocalTime.now().isAfter(expiryTime) )){
            this.state=MealStates.FULL;
        } else this.state = MealStates.PENDING;
        this.type=MealRequestType.PUBLIC;
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Build a private Meal Request
     * @param host host who request a meal
     * @param mealType  type of the meal
     * @param consumationType   type of consummation
     * @param payment   type of payment
     * @param description description of the meal request
     * @param date  date of the meal request
     * @param time  time of the meal request
     * @param expiryDate    expiration date
     * @param expiryTime    expiration time
     * @param price price
     * @param place place
     * @param allergy   allergy
     * @param mealsNumber   number of the meal
     * @param homeOwner      user to whom the request is sent
     * @throws Exception    if one of the parameters is null or if the expiration date is after the date or the host is equals to the homeOwner
     */
    public MealRequest(IUser host, String mealType, ConsumationType consumationType, PaymentType payment, String description,
                       LocalDate date, LocalTime time, LocalDate expiryDate, LocalTime expiryTime,
                       String price, String place, String allergy, int mealsNumber, IUser homeOwner)throws Exception {
        this(host,mealType,consumationType,payment,description,date,time,expiryDate,expiryTime,price,place,allergy,mealsNumber);
        if (host.equals(homeOwner)) throw new IllegalArgumentException("You cannot send a request to yourself");
        this.homeOwner=homeOwner;
        this.type = MealRequestType.PRIVATE;
    }

    @Override
    public IUser getHost() {
        return host;
    }

    @Override
    public String getMealType() {
        return this.mealType;
    }

    @Override
    public void setId(String id) {
        this.id=id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public IUser getHomeOwner() {
        return homeOwner;
    }

    @Override
    public void setHost(IUser host) {
        this.host = host;
    }

    @Override
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public LocalDate getDate() {
        return this.date;
    }

    @Override
    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public LocalTime getTime() {
        return this.time;
    }

    @Override
    public void setExpiryDate(LocalDate expiringDate) {
        this.expiryDate = expiringDate;
    }

    @Override
    public LocalDate getExpiryDate() {
        return this.expiryDate;
    }

    @Override
    public void setExpiryTime(LocalTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    @Override
    public LocalTime getExpiryTime() {
        return this.expiryTime;
    }

    @Override
    public void setHomeOwner(IUser homeOwner) {
        this.homeOwner = homeOwner;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getAllergy() {
        return allergy;
    }

    @Override
    public int getMealsNumber() {
        return mealsNumber;
    }

    @Override
    public String getPlace() {
        return place;
    }

    @Override
    public ConsumationType getConsummationType() {
        return consummationType;
    }

    @Override
    public MealRequestType getType(){return this.type;}

    @Override
    public void setState(MealStates state) {
        this.state = state;
    }

    @Override
    public MealStates getState() {
        return state;
    }

    @Override
    public String getPrice() {
        return price;
    }

    @Override
    public PaymentType getPaymentType() {
        return paymentType;
    }

}