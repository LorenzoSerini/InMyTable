package it.unicam.cs.gp.inmytable.notification;

import it.unicam.cs.gp.inmytable.allmeals.meals.Food;
import it.unicam.cs.gp.inmytable.user.IUser;

public interface ISubscription<T extends IUser, F extends Food>{

    void accept();

    void refuse();

    T getUser();

    F getFood();

   SubscriptionStates getState();
}
