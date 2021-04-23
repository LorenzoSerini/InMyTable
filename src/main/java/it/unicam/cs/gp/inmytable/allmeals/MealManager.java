package it.unicam.cs.gp.inmytable.allmeals;

import it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest.PrivateMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest.PublicMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.meals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.allmeals.meals.PaymentType;
import it.unicam.cs.gp.inmytable.homewalls.HomeWall;
import it.unicam.cs.gp.inmytable.user.User;

import java.time.LocalDate;
import java.time.LocalTime;


public class MealManager {

	/**
	 * Build a new Meal
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
	public Meal createMeal(User homeOwner, int maxNumberUsers, LocalDate date, LocalTime time, LocalDate expiryDate, LocalTime expiryTime, String mealType, boolean freeSubscription, String place,
						   ConsumationType consumationType, String description, String ingredients, PaymentType payment, String price) throws Exception{
		Meal meal = new Meal(homeOwner, maxNumberUsers, date, time, expiryDate,expiryTime, mealType, freeSubscription, place,
				consumationType, description, ingredients, payment, price);
		HomeWall.getInstance().getMealCatalog().add(meal);
		return meal;
	}

	/**
	 * Create a public meal request
	 * @param host the host of the request
	 * @param date date of meal request
	 * @param expiryDate expiryDate of meal request
	 * @param place place of meal request
	 * @return MealRequest
	 * @throws Exception if one of the parameters is null
	 */
	public MealRequest createMealRequest(User host, String mealType, ConsumationType consumationType, PaymentType payment, String description, LocalDate date, LocalTime time, LocalDate expiryDate, LocalTime expiryTime, String price, String place, String allergy, int mealsNumber) throws Exception {
			PublicMealRequest mealRequest = new PublicMealRequest(host,mealType, consumationType, payment, description, date, time, expiryDate, expiryTime, price, place, allergy, mealsNumber);
			HomeWall.getInstance().getMealRequestCatalog().add(mealRequest);
			return mealRequest;
	}

	/**
	 * Create a private meal request
	 * @param host the host of the request
	 * @param date date of meal request
	 * @param expiryDate expiryDate of meal request
	 * @param place place of meal request
	 * @param homeOwner the owner receiving the request
	 * @return MealRequest
	 * @throws Exception if one of the parameters is null
	 */
	public MealRequest createMealRequest(User host, String mealType, ConsumationType consumationType, PaymentType payment, String description, LocalDate date, LocalTime time, LocalDate expiryDate, LocalTime expiryTime, String price, String place, String allergy, int mealsNumber, User homeOwner) throws Exception {
		return new PrivateMealRequest(host, mealType, consumationType, payment, description, date, time, expiryDate, expiryTime, price, place, allergy, mealsNumber, homeOwner);
	}

}