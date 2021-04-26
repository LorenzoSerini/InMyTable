package it.unicam.cs.gp.inmytable.allmeals.meals;

import it.unicam.cs.gp.inmytable.user.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Meal {

	private LocalDate date;
	private LocalTime time;
	private int maxNumberUsers;
	private int placesAvailable;
	private LocalDate expiryDate;
	private LocalTime expiryTime;
	private String mealType;
	private boolean freeSubscription;
	private String place;
	private MealStates state;
	private ConsumationType consumationType;
	private String description;
	private String ingredients;
	private PaymentType payment;
	private String price;
	private User homeOwner;
	private Set<User> userList;


	private String address; //TODO: DA AGGIUNGERE?



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
	 * @param consumationType	if is in place or takeaway
	 * @param description	short description of the meal
	 * @param payment	how payment is accepted
	 * @throws Exception if one of the parameters is null
	 */
	public Meal(User homeOwner, int maxNumberUsers, LocalDate date, LocalTime time, LocalDate expiryDate, LocalTime expiryTime, String mealType, boolean freeSubscription, String place,
				ConsumationType consumationType, String description, String ingredients, PaymentType payment, String price) throws Exception{
		if (homeOwner==null|| date ==null ||expiryDate ==null ||mealType==null ||place == null ||
				consumationType ==null|| description ==null || payment ==null) throw new NullPointerException("You must insert all!");
		if (LocalDate.now().isAfter(date) || (LocalDate.now().isEqual(date) && LocalTime.now().isAfter(time) )) throw new IllegalArgumentException("You cannot travel in time");
		if (date.isBefore(expiryDate) ||(LocalDate.now().isEqual(expiryDate) && time.isBefore(expiryTime))) throw new IllegalArgumentException("ExpirationTime should be after meal date");
		if(payment.compareTo(PaymentType.FREE)==0){
			this.price="0";
		}else this.price=price;
		this.homeOwner = homeOwner;
		this.maxNumberUsers = maxNumberUsers;
		this.placesAvailable = maxNumberUsers;
		this.date = date;
		this.time = time;
		this.expiryDate = expiryDate;
		this.expiryTime = expiryTime;
		this.mealType = mealType;
		this.freeSubscription = freeSubscription;
		this.place = place;
		this.consumationType = consumationType;
		this.description = description;
		this.ingredients = ingredients;
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
		this.placesAvailable--;
		if(userList.size()==maxNumberUsers) setState(MealStates.FULL);
	}

	/**
	 * Return the date of the meal
	 * @return	the date of the meal
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Return the time of the meal
	 * @return	the time of the meal
	 */
	public LocalTime getTime(){
		return time;
	}

	/**
	 * return the max number of meals available
	 * @return return the max number of meals available
	 */
	public int getMaxNumberUsers() {
		return maxNumberUsers;
	}

	/**
	 * return the places available for this meal
	 * @return
	 */
	public int getPlacesAvailable() {
		return maxNumberUsers-userList.size();
	}

	/**
	 * Return the expiring date of the meal
	 * @return	Return the expiring date of the meal
	 */
	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	/**
	 * Return the expiring time of the meal
	 * @return	Return the expiring time of the meal
	 */
	public LocalTime getExpiryTime(){
		return expiryTime;
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
	public String getIngredients(){return this.ingredients;}

	/**
	 *Return the meal payment type
	 * @return payment tipe
	 */
	public PaymentType getPayment() {
		return payment;
	}

	/**
	 *Return the meal price.
	 * if is a free meal return 0
	 * @return payment tipe
	 */
	public String getPrice() {
		return price;
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


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Meal)) return false;
		Meal meal = (Meal) o;
		return maxNumberUsers == meal.maxNumberUsers && date.equals(meal.date) && time.equals(meal.time) && expiryDate.equals(meal.expiryDate) && expiryTime.equals(meal.expiryTime) && mealType.equals(meal.mealType) && place.equals(meal.place) && description.equals(meal.description) && homeOwner.equals(meal.homeOwner) && address.equals(meal.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, time, maxNumberUsers, expiryDate, expiryTime, mealType, place, description, homeOwner, address);
	}
}
