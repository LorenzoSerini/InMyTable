package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.allmeals.ConsummationType;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.allmeals.PaymentType;
import it.unicam.cs.gp.inmytable.controllers.MealRequestsController;
import it.unicam.cs.gp.inmytable.user.User;
import org.springframework.stereotype.Service;



@Service
public class PublicMealRequestService {
    private MealRequestsController mealRequestsController;

    /**
     * used to set log user
     * @param logUser the log user
     * @throws Exception
     */
    public void setLogUser(User logUser) throws Exception {
        mealRequestsController=new MealRequestsController(logUser);
    }

    /**
     * this method is used to post a public meal request
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
    public void postAPublicMealRequest(String description, String mealType, String consummationType, String payment, String date, String expiryDate, String price, String place, String allergy, int mealsNumber) throws Exception {
        String startD = date.substring(0, 10);
        String startT = date.substring(11,16);
        String expiryD = expiryDate.substring(0, 10);
        String expiryT = expiryDate.substring(11,16);
        mealRequestsController.postPublicMealRequest(description, mealType, ConsummationType.valueOf(consummationType.toUpperCase()), PaymentType.valueOf(payment.toUpperCase()), startD, startT, expiryD, expiryT, price, place, allergy, mealsNumber);
    }

    /**
     * if i can accept meal request return true else false
     * @param logUser user
     * @param mealRequest meal request
     * @return
     */
    public boolean canIAccept(User logUser, MealRequest mealRequest){
        return !mealRequest.getHost().equals(logUser) && mealRequest.getHomeOwner()==null;
    }

    /**
     * this method is used to accept public meal request
     * @param mealRequest public meal request
     * @throws Exception
     */
    public void acceptPublicMealRequest(MealRequest mealRequest) throws Exception {
        mealRequestsController.acceptPublicMealRequest(mealRequest);
    }

    /**
     * returns italian translate consummation type string
     * @param mealRequest the meal request
     * @return italian translate consummation type string
     */
    public String getConsummationType(MealRequest mealRequest){
        switch (mealRequest.getConsummationType()){
            case AT_HOME: return "In casa di colui che lo ha pubblicato";
            case TAKEAWAY: return "Takeaway";
            case BOTH: return "Sia in casa di colui che ha pubblicato l'annuncio che Takeaway";
        }
        return null;
    }


}
