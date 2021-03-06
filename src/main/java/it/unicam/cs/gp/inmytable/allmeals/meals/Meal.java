package it.unicam.cs.gp.inmytable.allmeals.meals;


import it.unicam.cs.gp.inmytable.allmeals.ConsummationType;
import it.unicam.cs.gp.inmytable.allmeals.MealStates;
import it.unicam.cs.gp.inmytable.allmeals.PaymentType;
import it.unicam.cs.gp.inmytable.exception.ExpirationTimeException;
import it.unicam.cs.gp.inmytable.user.IUser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Meal implements IMeal{

	private LocalDate date;
	private LocalTime time;
	private int maxNumberUsers;
	private LocalDate expiryDate;
	private LocalTime expiryTime;
	private String mealType;
	private boolean freeSubscription;
	private String city;
	private String place;
	private MealStates state;
	private ConsummationType consummationType;
	private String description;
	private String ingredients;
	private PaymentType payment;
	private String price;
	private IUser homeOwner;
	private Set<IUser> userList;
	private String id;



	/**
	 * Meal constructor
	 * @param homeOwner who share the meal
	 * @param maxNumberUsers max number of available meals
	 * @param date date of the meal
	 * @param time time of the meal
	 * @param expiryDate expiring date of the meal
	 * @param expiryTime expiring time of the meal
	 * @param mealType type of meal
	 * @param freeSubscription	if the subscription of the meal is free or if is chosen by the homeOwner
	 * @param place	where the meal is
	 * @param consummationType	if is in place or takeaway
	 * @param description	short description of the meal
	 * @param payment	how payment is accepted
	 * @throws Exception if one of the parameters is null
	 */
	public Meal(IUser homeOwner, int maxNumberUsers, LocalDate date, LocalTime time, LocalDate expiryDate, LocalTime expiryTime, String mealType, boolean freeSubscription, String city, String place,
				ConsummationType consummationType, String description, String ingredients, PaymentType payment, String price) throws Exception{
		if (homeOwner==null|| date ==null ||expiryDate ==null ||mealType==null ||place == null ||
				consummationType ==null|| description ==null || payment ==null) throw new NullPointerException("You must insert all!");
		if (date.isBefore(expiryDate) )
			throw new ExpirationTimeException( date + " is before " + expiryDate);
		if (date.isEqual(expiryDate) && time.isBefore(expiryTime)) throw new
				ExpirationTimeException(time + " is before " + expiryTime);
		if(payment.compareTo(PaymentType.FREE)==0){
			this.price="0";
		}else this.price=price;
		this.homeOwner = homeOwner;
		this.maxNumberUsers = maxNumberUsers;
		this.date = date;
		this.time = time;
		this.expiryDate = expiryDate;
		this.expiryTime = expiryTime;
		this.mealType = mealType;
		this.freeSubscription = freeSubscription;
		this.city=city;
		this.place = place;
		this.consummationType = consummationType;
		this.description = description;
		this.ingredients = ingredients;
		this.payment = payment;
		if (LocalDate.now().isAfter(date) || (LocalDate.now().isEqual(date) && LocalTime.now().isAfter(time) )) {
			this.state= MealStates.EXPIRED;
		} else if (LocalDate.now().isAfter(expiryDate) || (LocalDate.now().isEqual(expiryDate) && LocalTime.now().isAfter(expiryTime) )){
			this.state=MealStates.FULL;
		} else this.state = MealStates.PENDING;
		this.userList = new HashSet<>();
		this.id = UUID.randomUUID().toString();
	}

	/**
	 * Setter for the meal state
	 * @param newState new meal state
	 */
	@Override
	public void setState(MealStates newState){
		this.state = newState;
	}

	/**
	 * Return the state of the meal
	 * @return	state of the meal
	 */
	@Override
	public MealStates getState(){
		return this.state;
	}

	/**
	 * Adding an user to the meal. If the number of the users is equals of maxNumber the meal changes state to FULL.
	 * @param user user to add.
	 */
	@Override
	public void addUser(IUser user){
		this.userList.add(user);
		if(userList.size()==maxNumberUsers) setState(MealStates.FULL);
	}

	/**
	 * Return the date of the meal
	 * @return	the date of the meal
	 */
	@Override
	public LocalDate getDate() {
		return date;
	}

	@Override
	public void setTime(LocalTime time) {
		this.time=time;
	}

	/**
	 * Return the time of the meal
	 * @return	the time of the meal
	 */
	@Override
	public LocalTime getTime(){
		return time;
	}

	@Override
	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate=expiryDate;
	}

	/**
	 * return the max number of meals available
	 * @return return the max number of meals available
	 */
	@Override
	public int getMaxNumberUsers() {
		return maxNumberUsers;
	}

	/**
	 * return the places available for this meal
	 * @return the places available for this meal
	 */
	@Override
	public int getPlacesAvailable() {
		return maxNumberUsers-userList.size();
	}

	/**
	 * Return the expiring date of the meal
	 * @return	Return the expiring date of the meal
	 */
	@Override
	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	@Override
	public void setExpiryTime(LocalTime expiryTime) {
		this.expiryTime=expiryTime;
	}

	/**
	 * Return the expiring time of the meal
	 * @return	Return the expiring time of the meal
	 */
	@Override
	public LocalTime getExpiryTime(){
		return expiryTime;
	}

	@Override
	public void setDescription(String description) {
		this.description=description;
	}

	/**
	 * return the type of the meal
	 * @return mealType
	 */
	@Override
	public String getMealType() {
		return mealType;
	}


	@Override
	public void setId(String id) {
		this.id=id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * return true if the subscription is free
	 * @return true if the subscription is free
	 */
	@Override
	public boolean isFreeSubscription() {
		return freeSubscription;
	}


	/**
	 * Return the city of the meal
	 * @return	the city of the meal
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Return the place of the meal
	 * @return	the place of the meal
	 */
	@Override
	public String getPlace() {
		return place;
	}


	@Override
	public ConsummationType getConsummationType() {
		return consummationType;
	}


	@Override
	public String getDescription() {
		return description;
	}


	@Override
	public String getIngredients(){return this.ingredients;}

	/**
	 *Return the meal payment type
	 * @return payment tipe
	 */
	@Override
	public PaymentType getPaymentType() {
		return payment;
	}

	/**
	 *Return the meal price.
	 * if is a free meal return 0
	 * @return payment tipe
	 */
	@Override
	public String getPrice() {
		return price;
	}


	@Override
	public IUser getHomeOwner() {
		return homeOwner;
	}

	@Override
	public void setDate(LocalDate date) {
		this.date=date;
	}


	@Override
	public Set<IUser> getUserList() {
		return userList;
	}

}
