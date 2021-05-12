package it.unicam.cs.gp.inmytable.notification;

import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
import it.unicam.cs.gp.inmytable.user.IUser;

import javax.management.Notification;

public class MealSubscription<T extends IUser, F extends IMeal> extends Subscription<T,F>{

    public MealSubscription(T user, F meal){
        super(user, meal);
    }

    @Override
    public void accept() {
        getFood().addUser(getUser());
        setState(SubscriptionStates.ACCEPTED);
    }

    @Override
    public void refuse() {
        setState(SubscriptionStates.REFUSED);
    }


}
