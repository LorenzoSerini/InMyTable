package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.user.Feedback;
import it.unicam.cs.gp.inmytable.user.User;

import java.sql.SQLException;
import java.util.List;

public interface FeedbackPersistence extends Persistence{

    /**
     * registers the feedback in the database
     * @param feedback the feedback
     * @throws Exception
     */
    void registerFeedback(Feedback feedback) throws Exception;

    /**
     * returns all feedbacks
     * @return all feedbacks
     * @throws Exception
     */
    List<Feedback> getFeedbacks() throws Exception;

    /**
     * returns all feedback sent by user
     * @param user the user who submitted the feedback
     * @return all feedback sent by user
     */
    List<Feedback> getFeedbacksFrom(User user);

    /**
     * returns all feedback received by user
     * @param user the user who received feedbacks
     * @return all feedback received by user
     */
    List<Feedback> getFeedbacksTo(User user);

}
