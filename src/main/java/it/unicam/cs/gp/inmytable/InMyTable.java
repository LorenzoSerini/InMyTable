package it.unicam.cs.gp.inmytable;

import com.google.common.hash.HashCode;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.controllers.GuestController;
import it.unicam.cs.gp.inmytable.homewalls.HomeWall;
import it.unicam.cs.gp.inmytable.notification.Notification;
import it.unicam.cs.gp.inmytable.notification.Subscription;
import it.unicam.cs.gp.inmytable.persistence.MealPersistence;
import it.unicam.cs.gp.inmytable.persistence.UserDB;
import it.unicam.cs.gp.inmytable.user.User;
import it.unicam.cs.gp.inmytable.utility.UsersUtilities;
import it.unicam.cs.gp.inmytable.view.View;
import it.unicam.cs.gp.inmytable.view.spring.SpringView;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


public class InMyTable {
	public static void main(String[] args){
		View view = new SpringView();
		view.start();

		/*User defaultUser = new User("Johnny76", "john@example.com", "000 000000", "John", "Doe", "example".hashCode(), LocalDate.parse("1950-01-01"), "FISCALCODICE", "IDCODICE", "Macerata, Via prova 15", true);*/

	}
	}

