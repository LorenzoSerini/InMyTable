package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.allmeals.Food;
import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
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

    public void setLogUser(User logUser) throws Exception {
        this.userController=new UserController(logUser);
        this.mealRequestsController = new MealRequestsController(logUser);
        this.mealsController = new MealsController(logUser);
    }

    public void postFeedback(User to, int rating, String comment, Food food) throws Exception {
        this.userController.leaveFeedback(to, rating, comment, food);
    }

    public User getUser(String username) throws Exception {
        return userController.getUser(username);
    }

    public Food getFood(String foodId) throws Exception {
       if(mealsController.getMeal(foodId)!=null){
           return mealsController.getMeal(foodId);
       } else return mealRequestsController.getMealRequest(foodId);
    }


    public boolean iSendFeedback(IUser from, IUser to, Food food) throws Exception {
        for(Feedback feedback:from.getFeedbackBox().getFeedbacks()){
            if(feedback.getFrom().equals(from)&&feedback.getTo().equals(to)&&feedback.getFood().equals(food)){
                return true;
            }
        }
        return false;
    }


    public List<IUser> iSendFeedback(IUser from, List<IUser> users, Food food) {
        List<IUser> sendFeedback = new ArrayList<>();
        for(Feedback feedback:from.getFeedbackBox().getFeedbacks()){
            if(feedback.getFrom().equals(from)&&users.contains(feedback.getTo())&&feedback.getFood().equals(food)){
               sendFeedback.add(feedback.getTo());
            }
        }
        return sendFeedback;
    }


    public List<Feedback> getFromFeedbacks(IUser from){
       return from.getFeedbackBox().getFeedbacks().stream().filter(p->p.getFrom().equals(from)).collect(Collectors.toList());
    }

    public double getFromFeedbacksAverage(IUser from){
        double average=0;
        List<Feedback> fromFeedbacks;
        if(from.getFeedbackBox().getFeedbacks().stream().anyMatch(p -> p.getTo().equals(from))){
            fromFeedbacks = from.getFeedbackBox().getFeedbacks().stream().filter(p->p.getFrom().equals(from)).collect(Collectors.toList());
        } else return 5;
        for(Feedback f:fromFeedbacks){
            average+=f.getRating();
        }
        return average/fromFeedbacks.size();
    }

    public List<Feedback> getToFeedbacks(IUser to){
        return to.getFeedbackBox().getFeedbacks().stream().filter(p->p.getTo().equals(to)).collect(Collectors.toList());
    }

    public double getToFeedbacksAverage(IUser to){
        double average=0;
        List<Feedback> toFeedbacks;
        if(to.getFeedbackBox().getFeedbacks().stream().anyMatch(p -> p.getTo().equals(to))){
            toFeedbacks  = to.getFeedbackBox().getFeedbacks().stream().filter(p->p.getTo().equals(to)).collect(Collectors.toList());
        } else return 5;
        for(Feedback f:toFeedbacks){
            average+=f.getRating();
        }
        return average/toFeedbacks.size();
    }

    public Feedback getFeedback(IUser user, String feedbackId){
        for(Feedback f:user.getFeedbackBox().getFeedbacks()){
            if(f.getId().equals(feedbackId)) return f;
        }
        return null;
    }

}
