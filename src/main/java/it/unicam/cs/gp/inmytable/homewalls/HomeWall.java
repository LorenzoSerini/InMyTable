package it.unicam.cs.gp.inmytable.homewalls;

import it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest.PublicMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.allmeals.meals.MealStates;
import it.unicam.cs.gp.inmytable.user.User;

import java.time.LocalDate;
import java.time.LocalTime;

public class HomeWall {

	private static Catalog<Meal> mealCatalog;
	private static Catalog<PublicMealRequest> mealRequestCatalog;
	private static Catalog<User> userCatalog;
	private static HomeWall instance;

	/**
	 * Singleton constructor
	 */
	private HomeWall(){

	}

	/**
	 * Method to visit the homeWall
	 * @return instance of HomeWall
	 */
	public static HomeWall getInstance() {
		if (instance == null )
			instance = new HomeWall();
		return instance;
	}

	/**
	 * Add meal
	 * @param meal
	 */
	public void addMeal(Meal meal){
		mealCatalog.add(meal);
	}

	/**
	 * Return all catalog of meals
	 * @return	meal catalog
	 */
	public Catalog<Meal> getMealCatalog(){
		if (mealCatalog == null){
			mealCatalog = new Catalog<Meal>();
		}
		else
			mealCatalog.search(p->p.getExpiryDate().isAfter(LocalDate.now()) || (
				p.getExpiryDate().isEqual(LocalDate.now()) &&
						p.getExpiryTime().isBefore(LocalTime.now()))).forEach(u-> u.setState(MealStates.EXPIRED));
		return mealCatalog;
	}

	/**
	 * Return all meals request
	 * @return mealRequest catalog
	 */
	public Catalog<PublicMealRequest> getMealRequestCatalog(){
		if (mealRequestCatalog == null){
			mealRequestCatalog = new Catalog<PublicMealRequest>();
		}
		else
			mealRequestCatalog.search(p->p.getExpiryDate().isAfter(LocalDate.now()) || (
				p.getExpiryDate().isEqual(LocalDate.now()) &&
						p.getExpiryTime().isBefore(LocalTime.now()))).forEach(u-> u.setState(MealStates.EXPIRED));
		return mealRequestCatalog;
	}

	/**
	 * Catalog of all users
	 * @return	catalog of users
	 */
	public Catalog<User> getUserCatalog(){
		if (userCatalog == null){
			userCatalog = new Catalog<User>();
		}
		return userCatalog;
	}
}