package it.unicam.cs.gp.inmytable;

import it.unicam.cs.gp.inmytable.allmeals.meals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.PaymentType;
import it.unicam.cs.gp.inmytable.controllers.GuestController;
import it.unicam.cs.gp.inmytable.controllers.MealRequestsController;
import it.unicam.cs.gp.inmytable.controllers.MealsController;
import it.unicam.cs.gp.inmytable.controllers.UserController;
import it.unicam.cs.gp.inmytable.homewalls.HomeWall;
import it.unicam.cs.gp.inmytable.user.IUser;
import it.unicam.cs.gp.inmytable.user.User;
import it.unicam.cs.gp.inmytable.view.View;
import it.unicam.cs.gp.inmytable.view.spring.SpringView;



public class InMyTable {
	public static void main(String[] args){
		View view = new SpringView();
		view.start();

		try {

			/*GuestController guestController = new GuestController();

			User logUser = guestController.logIn("Jane88", "prova");
			MealsController mealsController = new MealsController(logUser);
			MealRequestsController mealsRequestController = new MealRequestsController(logUser);
			UserController userController = new UserController(logUser);

			mealsController.joinToMeal(HomeWall.getInstance().getMealCatalog().get(0));

			IUser logUser2;

			logUser2 = HomeWall.getInstance().getMealCatalog().get(0).getHomeOwner();

			System.out.println(logUser2.getMealNotifications().get(0).from().getUsername());
			System.out.println(logUser2.getMealNotifications().get(0).getMsg());
			System.out.println(logUser2.getMealNotifications().get(0).getSubscription().getState());


			logUser2= HomeWall.getInstance().getMealRequestCatalog().get(0).getHost();

			mealsRequestController.acceptPublicMealRequest(HomeWall.getInstance().getMealRequestCatalog().get(0));

			System.out.println(logUser2.getMealRequestNotifications().get(0).from().getUsername());
			System.out.println(logUser2.getMealRequestNotifications().get(0).getMsg());
			System.out.println(logUser2.getMealRequestNotifications().get(0).getSubscription().getState());*/



			/*ULTIMA PROVA FATTA !!!!!!!!!!!!!!!!!!!!!!
			GuestController guestController = new GuestController();

			User logUser = guestController.logIn("Jane88", "prova");
			User logUser2 = guestController.logIn("Lollock", "prova");
			//IUser logUser2 = HomeWall.getInstance().getMealRequestCatalog().get(0).getHost();
			MealsController mealsController = new MealsController(logUser);
			MealRequestsController mealsRequestController = new MealRequestsController(logUser);
			UserController userController = new UserController(logUser);

			mealsRequestController.postPrivateMealRequest(logUser2, "prova", "prova", ConsumationType.TAKEAWAY, PaymentType.CASH,
			"2021-05-20","20:00","2021-05-20","20:00", "prova", "prova", "prova",10);

			System.out.println(logUser2.getMealRequestNotifications().get(0).from().getUsername());
			System.out.println(logUser2.getMealRequestNotifications().get(0).getMsg());
			System.out.println(logUser2.getMealRequestNotifications().get(0).getSubscription().getState());


			MealRequestsController mealsRequestController2 = new MealRequestsController(logUser2);
			mealsRequestController2.acceptPrivateMealRequest(logUser2.getMealRequestNotifications().get(0).getSubscription());

			System.out.println(logUser.getMealRequestNotifications().get(0).from().getUsername());
			System.out.println(logUser.getMealRequestNotifications().get(0).getMsg());
			System.out.println(logUser.getMealRequestNotifications().get(0).getSubscription().getState());



			mealsRequestController.postPublicMealRequest("prova", "prova", ConsumationType.TAKEAWAY, PaymentType.CASH,
					"2021-05-20","20:00","2021-05-20","20:00", "prova", "prova", "prova",10);

			mealsRequestController2.acceptPublicMealRequest(HomeWall.getInstance().getMealRequestCatalog().get(1));

			System.out.println(logUser.getMealRequestNotifications().get(1).from().getUsername());
			System.out.println(logUser.getMealRequestNotifications().get(1).getMsg());
			System.out.println(logUser.getMealRequestNotifications().get(1).getSubscription().getState());

			//System.out.println(logUser2.getMealNotifications().get(0).from().getUsername());
			//System.out.println(logUser2.getMealNotifications().get(0).getMsg());
			//System.out.println(logUser2.getMealNotifications().get(0).getSubscription().getState());


				FINE ULTIMA PROVA-------------------------------
			 */


		/*	GuestController guestController = new GuestController();

			User logUser = guestController.logIn("Jane88", "prova");
			MealsController mealsController = new MealsController(logUser);
			UserController userController = new UserController(logUser);

			userController.joinToMeal(HomeWall.getInstance().getMealCatalog().get(0));

			IUser logUser2;

			logUser2= HomeWall.getInstance().getMealCatalog().get(0).getHomeOwner();


			System.out.println(logUser2.getMealNotifications().get(0).from().getUsername());
			System.out.println(logUser2.getMealNotifications().get(0).getMsg());
			System.out.println(logUser2.getMealNotifications().get(0).getSubscription().getState());

			System.out.println("\n\n");
			logUser2= HomeWall.getInstance().getMealRequestCatalog().get(0).getHost();
			userController.acceptPublicMealRequest(HomeWall.getInstance().getMealRequestCatalog().get(0));

			System.out.println(logUser2.getMealRequestNotifications().get(0).from().getUsername());
			System.out.println(logUser2.getMealRequestNotifications().get(0).getMsg());
			System.out.println(logUser2.getMealRequestNotifications().get(0).getSubscription().getState());
*/
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	}

