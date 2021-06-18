package it.unicam.cs.gp.inmytable.allmeals.mealrequest;

import it.unicam.cs.gp.inmytable.allmeals.Food;
import it.unicam.cs.gp.inmytable.user.*;

/**
 * Meal request interface
 */
public interface IMealRequest extends Food {

	/**
	 * return the meal request host
	 * @return meal request host
	 */
	IUser getHost();

	/**
	 * used to set a meal request host
	 * @param host meal request host
	 */
	void setHost(IUser host);

	/**
	 * return the host allergy
	 * @return host allergy
	 */
	String getAllergy();

	/**
	 * return meals number
	 * @return meals number
	 */
	int getMealsNumber();

	/**
	 * return meal request type
	 * @return MealRequestType
	 */
	MealRequestType getType();

	/**
	 * used to set meal request home owner
	 * @param homeOwner meal request home owner
	 */
	void setHomeOwner(IUser homeOwner);

}