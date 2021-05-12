package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.user.Feedback;
import it.unicam.cs.gp.inmytable.user.User;

import java.sql.SQLException;
import java.util.List;

public interface FeedbackPersistence extends Persistence{

    void registerFeedback(Feedback feedback) throws Exception;

    List<Feedback> getFeedbacks() throws Exception;

    List<Feedback> getFeedbacksFrom(User user);

    List<Feedback> getFeedbacksTo(User user);

}
