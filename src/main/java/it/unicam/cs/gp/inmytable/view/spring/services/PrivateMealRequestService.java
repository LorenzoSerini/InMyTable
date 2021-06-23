package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.allmeals.ConsummationType;
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

    /**
     * used to set log user
     * @param logUser the log user
     * @throws Exception
     */
    public void setLogUser(User logUser) throws Exception {
        userController = new UserController(logUser);
        mealRequestsController = new MealRequestsController(logUser);
        this.me = logUser;
    }

    /**
     * return the user
     * @param username username
     * @return the user
     * @throws Exception
     */
    public User getUser(String username) throws Exception {
        return userController.getUser(username);
    }

    /**
     * if it's me return true else false
     * @param requestTo user
     * @return if it's me return true else false
     */
    public boolean itIsMe(User requestTo){return requestTo.equals(me);}

    /**
     * this method is used to post a private meal request
     * @param homeOwner the home owner of meal request
     * @param description meal request description
     * @param mealType meal request mealType
     * @param consummationType meal request consummationType
     * @param payment meal request payment
     * @param date meal request date
     * @param expiryDate meal request expiryDate
     * @param price meal request price
     * @param place meal request place
     * @param allergy meal request allergy
     * @param mealsNumber meal request mealsNumber
     * @throws Exception
     */
    public void postAPrivateMealRequest(User homeOwner, String description, String mealType, String consummationType, String payment, String date, String expiryDate, String price, String place, String allergy, int mealsNumber) throws Exception {
        String startD = date.substring(0, 10);
        String startT = date.substring(11,16);
        String expiryD = expiryDate.substring(0, 10);
        String expiryT = expiryDate.substring(11,16);
        mealRequestsController.postPrivateMealRequest(homeOwner,description, mealType, ConsummationType.valueOf(consummationType.toUpperCase()), PaymentType.valueOf(payment.toUpperCase()), startD, startT, expiryD, expiryT, price, place, allergy, mealsNumber);
    }

}
