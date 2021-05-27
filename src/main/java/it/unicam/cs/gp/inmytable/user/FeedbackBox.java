package it.unicam.cs.gp.inmytable.user;

import java.util.ArrayList;
import java.util.List;

public class FeedbackBox {
    private List<Feedback> feedbacks;

    public FeedbackBox(){
        feedbacks = new ArrayList<>();
    }


    public double getAverage(){
        double average = 0;
        for (Feedback feedback: feedbacks) {
            average += feedback.getRating();
        }
        return average/feedbacks.size();
    }

    public void addFeedback(Feedback feedback){
        feedbacks.add(feedback);
    }

    public List<Feedback> getFeedbacks(){
        return new ArrayList<>(this.feedbacks);
    }
}
