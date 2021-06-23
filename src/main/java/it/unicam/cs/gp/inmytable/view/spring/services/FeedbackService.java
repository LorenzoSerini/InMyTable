package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.allmeals.Food;
import it.unicam.cs.gp.inmytable.controllers.MealRequestsController;
import it.unicam.cs.gp.inmytable.controllers.MealsController;
import it.unicam.cs.gp.inmytable.controllers.UserController;
import it.unicam.cs.gp.inmytable.user.Feedback;
import it.unicam.cs.gp.inmytable.user.IUser;
import it.unicam.cs.gp.inmytable.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {
    UserController userController;
    MealRequestsController mealRequestsController;
    MealsController mealsController;

    /**
     * set log user
     * @param logUser log user
     * @throws Exception
     */
    public void setLogUser(User logUser) throws Exception {
        this.userController=new UserController(logUser);
        this.mealRequestsController = new MealRequestsController(logUser);
        this.mealsController = new MealsController(logUser);
    }

    /**
     * used to post a feedback
     * @param to the user who received the feedback
     * @param rating the feedback rating
     * @param comment the feedback comment
     * @param food the food
     * @throws Exception
     */
    public void postFeedback(User to, int rating, String comment, Food food) throws Exception {
        this.userController.leaveFeedback(to, rating, comment, food);
    }

    /**
     * returns a user
     * @param username the username
     * @return the user
     * @throws Exception
     */
    public User getUser(String username) throws Exception {
        return userController.getUser(username);
    }

    /**
     * return the food
     * @param foodId the food id
     * @return the food
     * @throws Exception
     */
    public Food getFood(String foodId) throws Exception {
       if(mealsController.getMeal(foodId)!=null){
           return mealsController.getMeal(foodId);
       } else return mealRequestsController.getMealRequest(foodId);
    }


    /**
     * returns true if from have been sent feedback
     * @param from user
     * @param to user
     * @param food food
     * @return
     * @throws Exception
     */
    public boolean iSendFeedback(IUser from, IUser to, Food food) throws Exception {
        for(Feedback feedback:from.getFeedbackBox().getFeedbacks()){
            if(feedback.getFrom().equals(from)&&feedback.getTo().equals(to)&&feedback.getFood().equals(food)){
                return true;
            }
        }
        return false;
    }


    /**
     *  returns true if from have been sent feedback
     * @param from user
     * @param users users list
     * @param food food
     * @return
     */
    public List<IUser> iSendFeedback(IUser from, List<IUser> users, Food food) {
        List<IUser> sendFeedback = new ArrayList<>();
        for(Feedback feedback:from.getFeedbackBox().getFeedbacks()){
            if(feedback.getFrom().equals(from)&&users.contains(feedback.getTo())&&feedback.getFood().equals(food)){
               sendFeedback.add(feedback.getTo());
            }
        }
        return sendFeedback;
    }

    /**
     * returns user from feedbacks
     * @param from user
     * @return user feedbacks
     */
    public List<Feedback> getFromFeedbacks(IUser from){
       return from.getFeedbackBox().getFeedbacks().stream().filter(p->p.getFrom().equals(from)).collect(Collectors.toList());
    }

    /**
     * returns user from feedbacks average
     * @param from the user
     * @return user from feedbacks average
     */
    public double getFromFeedbacksAverage(IUser from){
        return from.getFeedbackBox().getSentFeedbacksAverage();
    }

    /**
     * returns all feedbacks received by user to
     * @param to the user
     * @return all feedbacks received by user to
     */
    public List<Feedback> getToFeedbacks(IUser to){
        return to.getFeedbackBox().getFeedbacks().stream().filter(p->p.getTo().equals(to)).collect(Collectors.toList());
    }

    /**
     * returns feedbacks average received by user to
     * @param to the user
     * @return feedbacks average received by user to
     */
    public double getToFeedbacksAverage(IUser to){
        return to.getFeedbackBox().getReceivedFeedbacksAverage();
    }

    /**
     * return the user feedback
     * @param user the user
     * @param feedbackId feedback id
     * @return the user feedback
     */
    public Feedback getFeedback(IUser user, String feedbackId){
        for(Feedback f:user.getFeedbackBox().getFeedbacks()){
            if(f.getId().equals(feedbackId)) return f;
        }
        return null;
    }

}
