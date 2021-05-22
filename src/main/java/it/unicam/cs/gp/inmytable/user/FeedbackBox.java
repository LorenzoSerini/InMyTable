package it.unicam.cs.gp.inmytable.user;

import java.util.ArrayList;
import java.util.List;

public class FeedbackBox {
    private List<Feedback> feedbacks;
    private double average;

    public FeedbackBox(){
        feedbacks = new ArrayList<>();
        average = 0;
    }

    public double getAvg(){
        return average;
    }

    private double getRating(){
        double count = 0;
        for (Feedback feedback: feedbacks) {
            count += feedback.getRating();
        }
        return count/feedbacks.size();
    }

    public void addFeedback(Feedback feedback){
        feedbacks.add(feedback);
        average = getRating();
    }

    public List<Feedback> getFeedbacks(){
        return new ArrayList<>(this.feedbacks);
    }
}
