package it.unicam.cs.gp.inmytable;

import it.unicam.cs.gp.inmytable.allmeals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.MealStates;
import it.unicam.cs.gp.inmytable.allmeals.PaymentType;
import it.unicam.cs.gp.inmytable.controllers.MealsController;
import it.unicam.cs.gp.inmytable.view.View;
import it.unicam.cs.gp.inmytable.view.spring.SpringView;

public class InMyTable {
	public static void main(String[] args) {
		/*View view = new SpringView();
		view.start();*/
		MealsController mealsController = new MealsController();
		mealsController.cook("09-01-2020", "09-01-2020", 4, "Esotico", true, "Macerata, Via Roma 15", MealStates.PENDING, ConsumationType.TAKEAWAY, "prova", PaymentType.CASH);

	}

}
