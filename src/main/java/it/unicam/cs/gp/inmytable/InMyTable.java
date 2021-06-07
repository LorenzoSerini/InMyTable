package it.unicam.cs.gp.inmytable;

import it.unicam.cs.gp.inmytable.view.View;
import it.unicam.cs.gp.inmytable.view.spring.SpringView;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;


public class InMyTable {
	private final View view;


	public enum IN_MY_TABLE_TYPE {
		WEB
	}

	public InMyTable(View view) {
		this.view = view;
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			createInMyTable("WEB").start();
		} else {
			try {
				Objects.requireNonNull(createInMyTable(args[0])).start();
			} catch (IllegalArgumentException e) {
				System.err.println(args[0] + " InMyTable is unknown!");
			}
		}
	}


		public void start() {
			view.start();
		}


		public static InMyTable createInMyTable(String code) {
			switch (IN_MY_TABLE_TYPE.valueOf(code.toUpperCase())) {
				case WEB:
					return createInMyTableWeb();
			}
			return null;
		}


	public static InMyTable createInMyTableWeb() {
		return new InMyTable(new SpringView());
	}

}


