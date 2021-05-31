package it.unicam.cs.gp.inmytable.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FeedbackBox {
    private List<Feedback> feedbacks;
    private IUser user;

    public FeedbackBox(IUser user){
        feedbacks = new ArrayList<>();
        this.user=user;
    }


    public double getAverage(){
        double average = 0;
        for (Feedback feedback: feedbacks) {
            average += feedback.getRating();
        }
        return average/feedbacks.size();
    }

    public double getSentFeedbacksAverage(){
        double average = 0;
        if(feedbacks.stream().anyMatch(p -> p.getFrom().equals(this.user))){
            for (Feedback feedback: feedbacks.stream().filter(p->p.getFrom().equals(this.user)).collect(Collectors.toList())) {
                average += feedback.getRating();
            }
            return average/feedbacks.size();
        }else return 5;
    }


    public double getReceivedFeedbacksAverage(){
        double average = 0;
        if(feedbacks.stream().anyMatch(p -> p.getTo().equals(this.user))){
            for (Feedback feedback: feedbacks.stream().filter(p->p.getTo().equals(this.user)).collect(Collectors.toList())) {
                average += feedback.getRating();
            }
            return average/feedbacks.size();
        }else return 5;
    }


    public void addFeedback(Feedback feedback){
        feedbacks.add(feedback);
    }

    public List<Feedback> getFeedbacks(){
        return new ArrayList<>(this.feedbacks);
    }
}
