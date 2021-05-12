package it.unicam.cs.gp.inmytable.notification;

import it.unicam.cs.gp.inmytable.allmeals.mealrequest.IMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequestType;
import it.unicam.cs.gp.inmytable.user.IUser;

public class MealRequestSubscription <T extends IUser, F extends IMealRequest> extends Subscription<T,F>{


   public MealRequestSubscription(T user, F food){
       super(user ,food);
   }


    @Override
    public void accept() {
        if(getFood().getType().equals(MealRequestType.PUBLIC)) getFood().setHomeOwner(getUser());
        setState(SubscriptionStates.ACCEPTED);
    }

    @Override
    public void refuse() {
        if(getFood().getType().equals(MealRequestType.PRIVATE)){
            setState(SubscriptionStates.REFUSED);
        }
    }

}
