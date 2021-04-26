package it.unicam.cs.gp.inmytable.homewalls;

import it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest.PublicMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.user.User;

import java.time.LocalDate;

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
		return mealCatalog;
	}


	public Catalog<PublicMealRequest> getMealRequestCatalog(){
		if (mealRequestCatalog == null){
			mealRequestCatalog = new Catalog<PublicMealRequest>();
		}
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