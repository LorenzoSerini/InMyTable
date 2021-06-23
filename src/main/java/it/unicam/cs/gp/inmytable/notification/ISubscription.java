package it.unicam.cs.gp.inmytable.notification;

import it.unicam.cs.gp.inmytable.allmeals.Food;
import it.unicam.cs.gp.inmytable.user.IUser;

public interface ISubscription<T extends IUser, F extends Food>{

    /**
     * This method is used to accept the subscription
     */
    void accept();

    /**
     * This method is used to refuse the subscription
     */
    void refuse();

    /**
     * returns the subscription user
     * @return subscription user
     */
    T getUser();

    /**
     * returns the subscription food
     * @return subscription food
     */
    F getFood();

    /**
     * Set subscription state
     * @param state SubscriptionStates
     */
    void setState(SubscriptionStates state);

    /**
     * returns subscription state
     * @return SubscriptionStates
     */
   SubscriptionStates getState();


}
