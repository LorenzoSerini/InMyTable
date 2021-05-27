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

    public double getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public User getTo() {
        return to;
    }

    public User getFrom() {
        return from;
    }

    public Food getFood() {
        return this.food;
    }

    public void setId(String id) {
        this.id=id;
    }

    public String getId() {
        return this.id;
    }
}
