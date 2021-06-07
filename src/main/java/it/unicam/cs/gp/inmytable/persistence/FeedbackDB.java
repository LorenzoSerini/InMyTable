package it.unicam.cs.gp.inmytable.persistence;


import it.unicam.cs.gp.inmytable.allmeals.Food;
import it.unicam.cs.gp.inmytable.user.Feedback;
import it.unicam.cs.gp.inmytable.user.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FeedbackDB extends DBPersistence implements FeedbackPersistence{
    private String sql;
    private static List<Feedback> feedbackList;

    public FeedbackDB() throws Exception {
        super();
        if(feedbackList==null) {
            feedbackList = new ArrayList<>();
            fillFeedbackList();
        }
    }

    public FeedbackDB(String connectionString, String username, String password) throws Exception {
        super(connectionString,username,password);
        if(feedbackList==null) {
            feedbackList = new ArrayList<>();
            fillFeedbackList();
        }
    }


    private void fillFeedbackList() throws SQLException {
        String sql = "Select * from FeedBack";
        setData(sql);
        while (getData().next()) {
            User from = getUsers().get(getData().getString("FromUser"));
            User to = getUsers().get(getData().getString("ToUser"));
            Food food;
            if(getMealsMap().containsKey(getData().getString("FoodId"))){
                food= getMealsMap().get(getData().getString("FoodId"));
            }else food = getMealsRequestMap().getOrDefault(getData().getString("FoodId"), null);
            Feedback feedback = new Feedback(from, to, getData().getInt("Rating"), getData().getString("Comment"), food);
            feedback.setId(getData().getString("Id"));
            feedbackList.add(feedback);
            from.getFeedbackBox().addFeedback(feedback);
            to.getFeedbackBox().addFeedback(feedback);
        }
    }

    @Override
    public void registerFeedback(Feedback feedback) throws SQLException {
        this.sql = "insert into FeedBack(Id, FromUser, ToUser, Rating, Comment, FoodId) values (?,?,?,?,?,?)";
        PreparedStatement prepStat = getConnection().prepareStatement(this.sql);
        prepStat.setString(1, feedback.getId());
        prepStat.setString(2, feedback.getFrom().getUsername());
        prepStat.setString(3, feedback.getTo().getUsername());
        prepStat.setDouble(4, feedback.getRating());
        prepStat.setString(5, feedback.getComment());
        prepStat.setString(6, feedback.getFood().getId());
        prepStat.executeUpdate();
        feedbackList.add(feedback);
    }

    @Override
    public List<Feedback> getFeedbacks() {
        return feedbackList;
    }

    @Override
    public List<Feedback> getFeedbacksFrom(User user) {
        return feedbackList.stream().filter(u->u.getFrom().equals(user)).collect(Collectors.toList());
    }

    @Override
    public List<Feedback> getFeedbacksTo(User user) {
        return feedbackList.stream().filter(u->u.getTo().equals(user)).collect(Collectors.toList());
    }
}
