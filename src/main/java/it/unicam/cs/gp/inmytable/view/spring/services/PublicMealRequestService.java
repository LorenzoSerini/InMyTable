package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.allmeals.mealrequest.IMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.allmeals.meals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.allmeals.meals.PaymentType;
import it.unicam.cs.gp.inmytable.controllers.MealRequestsController;
import it.unicam.cs.gp.inmytable.controllers.MealsController;
import it.unicam.cs.gp.inmytable.notification.SubscriptionNotification;
import it.unicam.cs.gp.inmytable.user.IUser;
import it.unicam.cs.gp.inmytable.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class PublicMealRequestService {
   // private MealsController mealsController;
    private MealRequestsController mealRequestsController;


    public void setLogUser(User logUser) throws Exception {
        mealRequestsController=new MealRequestsController(logUser);
    }

    public void postAPublicMealRequest(String description, String mealType, String consummationType, String payment, String date, String expiryDate, String price, String place, String allergy, int mealsNumber) throws Exception {
        String startD = date.substring(0, 10);
        String startT = date.substring(11,16);
        String expiryD = expiryDate.substring(0, 10);
        String expiryT = expiryDate.substring(11,16);
        mealRequestsController.postPublicMealRequest(description, mealType, ConsumationType.valueOf(consummationType.toUpperCase()), PaymentType.valueOf(payment.toUpperCase()), startD, startT, expiryD, expiryT, price, place, allergy, mealsNumber);
    }

    public boolean canIAccept(User logUser, MealRequest mealRequest){
        return !mealRequest.getHost().equals(logUser) && mealRequest.getHomeOwner()==null;
    }

    public void acceptPublicMealRequest(MealRequest mealRequest) throws Exception {
        mealRequestsController.acceptPublicMealRequest(mealRequest);
    }

    public String getConsummationType(MealRequest mealRequest){
        switch (mealRequest.getConsummationType()){
            case AT_HOME: return "In casa di colui che lo ha pubblicato";
            case TAKEAWAY: return "Takeaway";
            case BOTH: return "Sia in casa di colui che ha pubblicato l'annuncio che Takeaway";
        }
        return null;
    }


}
