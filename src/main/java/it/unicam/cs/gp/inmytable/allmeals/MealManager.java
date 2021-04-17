package it.unicam.cs.gp.inmytable.allmeals;

import it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest.PrivateMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest.PublicMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.meals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.allmeals.meals.PaymentType;
import it.unicam.cs.gp.inmytable.homewalls.HomeWall;
import it.unicam.cs.gp.inmytable.user.User;

import java.util.Date;

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
	public Meal createMeal(User homeOwner, int maxNumberUsers, Date date, Date expiryDate, String mealType, boolean freeSubscription, String place,
						   ConsumationType consumationType, String description, PaymentType payment) throws Exception{
		Meal meal = new Meal(homeOwner, maxNumberUsers, date, expiryDate, mealType, freeSubscription, place,
				consumationType, description, payment);
		HomeWall.getInstance().getMealCatalog().add(meal);
		return meal;
	}

	public MealRequest createMealRequest(User host, Date date, Date expiryDate, String place, User homeOwner) throws Exception {
		if (homeOwner == null ){
			PublicMealRequest mealRequest = new PublicMealRequest(host,date,expiryDate,place);
			HomeWall.getInstance().getMealRequestCatalog().add(mealRequest);
			return mealRequest;
		}else {
			PrivateMealRequest mealRequest = new PrivateMealRequest(host,date,expiryDate,place, homeOwner);
			return mealRequest;
		}
	}

}