package it.unicam.cs.gp.inmytable.user;

import it.unicam.cs.gp.inmytable.allmeals.Food;

import java.util.UUID;

public class Feedback {
    private double rating;
    private String comment;
    private User to;
    private User from;
    private String id;
    private Food food;


    public Feedback(User from, User to, double rating , String comment, Food food ){
        this.from = from;
        this.to = to;
        this.rating = rating;
        this.comment=comment;
        this.food = food;
        this.id = UUID.randomUUID().toString();
    }

    /**
     * returns feedback rating
     * @return feedback rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * returns feedback comment
     * @return feedback comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * returns the user who received feedback
     * @return the user who received feedback
     */
    public User getTo() {
        return to;
    }

    /**
     * returns the user who sent feedback
     * @return the user who sent feedback
     */
    public User getFrom() {
        return from;
    }

    /**
     * returns the food for which the user was rated
     * @return the food for which the user was rated
     */
    public Food getFood() {
        return this.food;
    }

    /**
     * used to set feedback id
     * @param id the feedback di
     */
    public void setId(String id) {
        this.id=id;
    }

    /**
     * returns feedback id
     * @return feedback id
     */
    public String getId() {
        return this.id;
    }
}
