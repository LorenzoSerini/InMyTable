package it.unicam.cs.gp.inmytable.allmeals.meals;

import it.unicam.cs.gp.inmytable.user.User;

import java.util.*;

public class Meal {

	private Date date;
	private int maxNumberUsers;
	private Date expiryDate;
	private String mealType;
	private boolean freeSubscription;
	private String place;
	private MealStates state;
	private ConsumationType consumationType;
	private String description;
	private PaymentType payment;
	private User homeOwner;
	private Set<User> userList;



	/**
	 * Meal constructor
	 * @param homeOwner who share the meal
	 * @param maxNumberUsers max number of available meals
	 * @param date date of the meal
	 * @param expiryDate expiring date of the meal
	 * @param mealType type of meal
	 * @param freeSubscription	if the subscription of the meal is free or if is chosen by the homeOwner
	 * @param place	where the meal is
	 * @param consumationType	if is in place or takeaway
	 * @param description	short description of the meal
	 * @param payment	how payment is accepted
	 * @throws Exception if one of the parameters is null
	 */
	public Meal(User homeOwner, int maxNumberUsers, Date date, Date expiryDate, String mealType, boolean freeSubscription, String place,
				ConsumationType consumationType, String description, PaymentType payment) throws Exception{
		if (homeOwner==null|| date ==null ||expiryDate ==null ||mealType==null ||place == null ||
				consumationType ==null|| description ==null || payment ==null) throw new NullPointerException("You must insert all!");
		if (new Date().after(date)) throw new IllegalArgumentException("You cannot travel in time");
		if (date.before(expiryDate)) throw new IllegalArgumentException("ExpirationTime should be after meal date");
		this.homeOwner = homeOwner;
		this.maxNumberUsers = maxNumberUsers;
		this.date = date;
		this.expiryDate = expiryDate;
		this.mealType = mealType;
		this.freeSubscription = freeSubscription;
		this.place = place;
		this.consumationType = consumationType;
		this.description = description;
		this.payment = payment;
		this.state = MealStates.PENDING;
		this.userList = new HashSet<User>();

	}

	/**
	 * Setter for the meal state
	 * @param newState new meal state
	 */
	public void setState(MealStates newState){
		this.state = newState;
	}

	/**
	 * Return the state of the meal
	 * @return	state of the meal
	 */
	public MealStates getState(){
		return state;
	}

	/**
	 * Adding an user to the meal. If the number of the users is equals of maxNumber the meal changes state to FULL.
	 * @param user user to add.
	 */
	public void addUser(User user){
		this.userList.add(user);
		if(userList.size()==maxNumberUsers) setState(MealStates.FULL);
	}

	/**
	 * Return the date of the meal
	 * @return	the date of the meal
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * return the max number of meals available
	 * @return return the max number of meals available
	 */
	public int getMaxNumberUsers() {
		return maxNumberUsers;
	}

	/**
	 * Return the expiring date of the meal
	 * @return	Return the expiring date of the meal
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * return the type of the meal
	 * @return mealType
	 */
	public String getMealType() {
		return mealType;
	}

	/**
	 * return true if the subscription is free
	 * @return true if the subscription is free
	 */
	public boolean isFreeSubscription() {
		return freeSubscription;
	}

	/**
	 * Return the place of the meal
	 * @return	the place of the meal
	 */
	public String getPlace() {
		return place;
	}

	/**
	 *
	 * @return
	 */
	public ConsumationType getConsumationType() {
		return consumationType;
	}

	/**
	 *
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 *
	 * @return
	 */
	public PaymentType getPayment() {
		return payment;
	}

	/**
	 *
	 * @return
	 */
	public User getHomeOwner() {
		return homeOwner;
	}

	/**
	 *
	 * @return
	 */
	public Set<User> getUserList() {
		return userList;
	}




}
