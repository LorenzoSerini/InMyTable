package it.unicam.cs.gp.inmytable.persistence;


import it.unicam.cs.gp.inmytable.user.Feedback;
import it.unicam.cs.gp.inmytable.user.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FeedbackDB extends DBPersistence implements FeedbackPersistence{
    private String sql;
    private List<Feedback> feedbackList;

    public FeedbackDB() throws Exception {
        super();
        feedbackList = new ArrayList<>();
        fillFeedbackList();
    }

    public FeedbackDB(String connectionString, String username, String password) throws Exception {
        super(connectionString,username,password);
        feedbackList = new ArrayList<>();
        fillFeedbackList();
    }


    private void fillFeedbackList() throws SQLException {
        String sql = "Select * from FeedBack";
        setData(sql);
        while (getData().next()) {
            User from = getUsers().get(getData().getString("From"));
            User to = getUsers().get(getData().getString("To"));
            Feedback feedback = new Feedback(from, to, getData().getInt("Rating"), getData().getString("Comment"));
            feedbackList.add(feedback);
        }
    }

    @Override
    public void registerFeedback(Feedback feedback) throws SQLException {
        this.sql = "insert into FeedBack(Id, From, To, Rating, Comment) values (?,?,?,?,?)";
        PreparedStatement prepStat = getConnection().prepareStatement(this.sql);
        prepStat.setInt(1, feedback.hashCode());
        prepStat.setString(2, feedback.getFrom().getUsername());
        prepStat.setString(3, feedback.getTo().getUsername());
        prepStat.setInt(4, feedback.getRating());
        prepStat.setString(5, feedback.getComment());
        prepStat.executeUpdate();
        this.feedbackList.add(feedback);
    }

    @Override
    public List<Feedback> getFeedbacks() throws SQLException {
        return new ArrayList<>(this.feedbackList);
    }

    @Override
    public List<Feedback> getFeedbacksFrom(User user) {
        return this.feedbackList.stream().filter(u->u.getFrom().equals(user)).collect(Collectors.toList());
    }

    @Override
    public List<Feedback> getFeedbacksTo(User user) {
        return this.feedbackList.stream().filter(u->u.getTo().equals(user)).collect(Collectors.toList());
    }
}
