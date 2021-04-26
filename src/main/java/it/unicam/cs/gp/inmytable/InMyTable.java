package it.unicam.cs.gp.inmytable;

import com.google.common.hash.HashCode;
import it.unicam.cs.gp.inmytable.persistence.DBUser;
import it.unicam.cs.gp.inmytable.persistence.IDBUser;
import it.unicam.cs.gp.inmytable.user.User;
import it.unicam.cs.gp.inmytable.view.View;
import it.unicam.cs.gp.inmytable.view.spring.SpringView;

import java.sql.SQLException;
import java.time.LocalDate;


public class InMyTable {
	public static void main(String[] args){
		//View view = new SpringView();
		//view.start();
		User defaultUser = new User("Johnny76", "john@example.com", "000 000000", "John", "Doe", "example", LocalDate.parse("1950-01-01"), "FISCALCODICE", "IDCODICE", "Macerata, Via prova 15", true);

		try {
			IDBUser idbUser = new DBUser();
			idbUser.registerUser(defaultUser);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}
	}

