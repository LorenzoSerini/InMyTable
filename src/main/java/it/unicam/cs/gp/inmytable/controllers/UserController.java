package it.unicam.cs.gp.inmytable.controllers;

import it.unicam.cs.gp.inmytable.user.Feedback;
import it.unicam.cs.gp.inmytable.user.User;

public class UserController {
    private User user;

    /**
     * Build an UserController for the user
     * @param user  user
     */
    public UserController(User user){
        this.user = user;
    }

    /**
     * Method for leave a comment to an other user
     * @param to  user who receive the feedback
     * @param rating    rating of the feedback
     * @param comment   comment of the feedback
     */
    public void leaveFeedback(User to, int rating, String comment){
        if (to ==null || comment == null) throw new NullPointerException("One parameter is null");
        if (user.equals(to)) throw new IllegalArgumentException("You cannot leave a comment to yourself!");
        to.getFeedbackBox().addFeedback(new Feedback(this.user, to,rating,comment));
    }
}
