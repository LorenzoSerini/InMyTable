package it.unicam.cs.gp.inmytable.homewalls;

import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.allmeals.Food;
import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.allmeals.MealStates;

import java.time.LocalDate;
import java.time.LocalTime;

public class HomeWall {

	private static Catalog<Meal> mealCatalog;
	private static Catalog<MealRequest> mealRequestCatalog;
	private static HomeWall instance;

	/**
	 * Singleton constructor
	 */
	private HomeWall(){
		mealCatalog = new Catalog<Meal>();
		mealRequestCatalog = new Catalog<MealRequest>();
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
		} else updateCatalog(mealCatalog);

		/*	mealCatalog.search(p->p.getExpiryDate().isBefore(LocalDate.now()) || (
				p.getExpiryDate().isEqual(LocalDate.now()) &&
						p.getExpiryTime().isBefore(LocalTime.now()))).forEach(u-> u.setState(MealStates.FULL));


		mealCatalog.search(p->p.getDate().isBefore(LocalDate.now()) || (
				p.getDate().isEqual(LocalDate.now()) &&
						p.getTime().isBefore(LocalTime.now()))).forEach(u-> u.setState(MealStates.EXPIRED));*/

			return mealCatalog;
	}


	/**
	 * Return all meals request
	 * @return mealRequest catalog
	 */
	public Catalog<MealRequest> getMealRequestCatalog(){
		if (mealRequestCatalog == null){
			mealRequestCatalog = new Catalog<MealRequest>();
		}else updateCatalog(mealRequestCatalog);

			/*mealRequestCatalog.search(p->p.getExpiryDate().isBefore(LocalDate.now()) || (
				p.getExpiryDate().isEqual(LocalDate.now()) &&
						p.getExpiryTime().isBefore(LocalTime.now()))).forEach(u-> u.setState(MealStates.EXPIRED));*/

		return mealRequestCatalog;
	}


	private void updateCatalog(Catalog<? extends Food> catalog){
		catalog.search(p->p.getExpiryDate().isBefore(LocalDate.now()) || (
				p.getExpiryDate().isEqual(LocalDate.now()) &&
						p.getExpiryTime().isBefore(LocalTime.now()))).forEach(u-> u.setState(MealStates.FULL));

		catalog.search(p->p.getDate().isBefore(LocalDate.now()) || (
				p.getDate().isEqual(LocalDate.now()) &&
						p.getTime().isBefore(LocalTime.now()))).forEach(u-> u.setState(MealStates.EXPIRED));
	}

}