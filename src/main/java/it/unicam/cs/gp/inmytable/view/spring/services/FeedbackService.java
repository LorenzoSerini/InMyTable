package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.controllers.MealRequestsController;
import it.unicam.cs.gp.inmytable.controllers.MealsController;
import it.unicam.cs.gp.inmytable.controllers.UserController;
import it.unicam.cs.gp.inmytable.user.IUser;
import it.unicam.cs.gp.inmytable.user.User;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    UserController userController;

    public void setLogUser(User logUser) throws Exception {
        this.userController=new UserController(logUser);
    }

    public void postFeedback(User to, int rating, String comment) throws Exception {
        this.userController.leaveFeedback(to, rating, comment);
    }

    public User getUser(String username) throws Exception {
        return userController.getUser(username);
    }
}
