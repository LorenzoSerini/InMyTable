package it.unicam.cs.gp.inmytable.allmeals.mealrequest;

import it.unicam.cs.gp.inmytable.allmeals.meals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.allmeals.meals.MealStates;
import it.unicam.cs.gp.inmytable.allmeals.meals.PaymentType;
import it.unicam.cs.gp.inmytable.user.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;


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

    /**
     * MealRequest constructor
     *
     * @param host       host who create the MealRequest
     * @param date       meal's date
     * @param expiryDate meal's expiring date
     * @param place      where host wants to eat
     */
    public MealRequest(IUser host, String mealType, ConsumationType consumationType, PaymentType payment, String description, LocalDate date, LocalTime time, LocalDate expiryDate, LocalTime expiryTime, String price, String place, String allergy, int mealsNumber) {
        if (date.isBefore(expiryDate) || (LocalDate.now().isEqual(expiryDate) && time.isBefore(expiryTime)))
            throw new IllegalArgumentException("ExpirationTime should be after meal date");
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
        } else if (LocalDate.now().isAfter(expiryDate) || (LocalDate.now().isEqual(expiryDate) && LocalTime.now().isAfter(expiryTime))) {
            this.state = MealStates.FULL;
        } else {
            this.state = MealStates.PENDING;
        }
        this.type=MealRequestType.PUBLIC;
    }

    public MealRequest(IUser host, String mealType, ConsumationType consumationType, PaymentType payment, String description, LocalDate date, LocalTime time, LocalDate expiryDate, LocalTime expiryTime, String price, String place, String allergy, int mealsNumber, User homeOwner) {
        this(host,mealType,consumationType,payment,description,date,time,expiryDate,expiryTime,price,place,allergy,mealsNumber);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meal)) return false;
        MealRequest mealRequest = (MealRequest) o;
        return mealsNumber == mealRequest.mealsNumber && date.equals(mealRequest.date) && time.equals(mealRequest.time) && expiryDate.equals(mealRequest.expiryDate) && expiryTime.equals(mealRequest.expiryTime) && mealType.equals(mealRequest.mealType) && place.equals(mealRequest.place) && description.equals(mealRequest.description) && homeOwner.equals(mealRequest.homeOwner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(allergy, type, mealsNumber, mealType, place, description, price);
    }
}