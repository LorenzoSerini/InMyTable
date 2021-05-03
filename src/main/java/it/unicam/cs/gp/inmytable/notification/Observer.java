package it.unicam.cs.gp.inmytable.notification;

public interface Observer<E> {

	void update();

	E getObserver();

}