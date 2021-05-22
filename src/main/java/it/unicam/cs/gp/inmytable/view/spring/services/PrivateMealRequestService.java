package it.unicam.cs.gp.inmytable.view.spring.services;



import it.unicam.cs.gp.inmytable.allmeals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.PaymentType;
import it.unicam.cs.gp.inmytable.controllers.MealRequestsController;
import it.unicam.cs.gp.inmytable.controllers.UserController;
import it.unicam.cs.gp.inmytable.user.User;
import org.springframework.stereotype.Service;

@Service
public class PrivateMealRequestService {
    private UserController userController;
    private MealRequestsController mealRequestsController;
    private User me;

    public void setLogUser(User logUser) throws Exception {
        userController = new UserController(logUser);
        mealRequestsController = new MealRequestsController(logUser);
        this.me = logUser;
    }

    public User getUser(String username) throws Exception {
        return userController.getUser(username);
    }

    public boolean itIsMe(User requestTo){return requestTo.equals(me);}

    public void postAPrivateMealRequest(User homeOwner, String description, String mealType, String consummationType, String payment, String date, String expiryDate, String price, String place, String allergy, int mealsNumber) throws Exception {
        String startD = date.substring(0, 10);
        String startT = date.substring(11,16);
        String expiryD = expiryDate.substring(0, 10);
        String expiryT = expiryDate.substring(11,16);
        mealRequestsController.postPrivateMealRequest(homeOwner,description, mealType, ConsumationType.valueOf(consummationType.toUpperCase()), PaymentType.valueOf(payment.toUpperCase()), startD, startT, expiryD, expiryT, price, place, allergy, mealsNumber);
    }

}
