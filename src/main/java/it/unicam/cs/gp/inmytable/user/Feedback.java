package it.unicam.cs.gp.inmytable.user;

import java.util.UUID;

public class Feedback {
    private int rating;
    private String comment;
    private User to;
    private User from;
    private String id;

    public Feedback(User from, User to, int rating , String comment ){
        this.from = from;
        this.to = to;
        this.rating = rating;
        this.comment=comment;
        this.id = UUID.randomUUID().toString();
    }

    public int getRating() {
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

    public void setId(String id) {
        this.id=id;
    }

    public String getId() {
        return this.id;
    }
}
