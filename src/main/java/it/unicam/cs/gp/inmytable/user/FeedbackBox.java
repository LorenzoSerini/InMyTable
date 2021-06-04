package it.unicam.cs.gp.inmytable.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FeedbackBox {
    private List<Feedback> feedbacks;
    private IUser user;
    private double sentFeedbacksAverage;
    private double receivedFeedbacksAverage;

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
        List<Feedback> fromFeedbacks;
        if(this.getFeedbacks().stream().anyMatch(p -> p.getFrom().equals(this.user))){
            fromFeedbacks = this.getFeedbacks().stream().filter(p->p.getFrom().equals(this.user)).collect(Collectors.toList());
        } else return 5;
        for(Feedback f:fromFeedbacks){
            average+=f.getRating();
        }
        this.sentFeedbacksAverage=average/fromFeedbacks.size();
        //return average/fromFeedbacks.size();
        return this.sentFeedbacksAverage;
    }


    public double getReceivedFeedbacksAverage(){
        double average=0;
        List<Feedback> toFeedbacks;
        if(this.feedbacks.stream().anyMatch(p -> p.getTo().equals(this.user))){
            toFeedbacks  = this.feedbacks.stream().filter(p->p.getTo().equals(this.user)).collect(Collectors.toList());
        } else return 5;
        for(Feedback f:toFeedbacks){
            average+=f.getRating();
        }
        this.receivedFeedbacksAverage=average/toFeedbacks.size();
        return receivedFeedbacksAverage;
        //return average/toFeedbacks.size();
    }


    public void addFeedback(Feedback feedback){
        feedbacks.add(feedback);
    }

    public List<Feedback> getFeedbacks(){
        return new ArrayList<>(this.feedbacks);
    }
}
