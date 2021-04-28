package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.notification.Subscription;
import it.unicam.cs.gp.inmytable.user.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class MealDB extends DBConnection implements MealPersistence{
    private String sql;

    public MealDB() throws SQLException {
        super();
    }

    public MealDB(String connectionString, String username, String password) throws SQLException{
        super(connectionString,username,password);
    }

    @Override
    public void registerMeal(User homeOwner, Meal meal) throws SQLException {
       /* this.sql = "insert into meal(Id, HomeOwner, Date, Time, ExpiringDate, ExpiringTime, MealType, Place, ConsumationType, Description, Ingredients, MaxNumberUsers, Payment, Price, MealState) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement prepStat = getConnection().prepareStatement(this.sql);
        prepStat.setInt(1, meal.hashCode());
        prepStat.setString(2, user.getFirstName());
        prepStat.setString(3, user.getLastName());
        prepStat.setString(4, user.getEmail());
        prepStat.setInt(5, user.getPassword());
        prepStat.setString(6, user.getFiscalCode());
        prepStat.setString(7, user.getId());
        prepStat.setString(8, String.valueOf(user.getBirth()));
        prepStat.setString(9, user.getTelephoneNumber());
        prepStat.setString(10, user.getAddress());
        prepStat.setBoolean(11, user.getAvailableToRequests());
        prepStat.executeUpdate();*/
    }

    @Override
    public void registerUserToMeal(User host, Meal meal, Subscription subscription) {

    }

    @Override
    public List<Meal> getMealsList() {
        return null;
    }
}
