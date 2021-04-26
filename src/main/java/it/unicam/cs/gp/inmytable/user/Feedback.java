package it.unicam.cs.gp.inmytable.user;

public class Feedback {
    private int rating;
    private String comment;
    private User to;
    private User from;

    public Feedback(User from, User to, int rating , String comment ){
        this.from = from;
        this.to = to;
        this.rating = rating;
        this.comment=comment;
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
}
