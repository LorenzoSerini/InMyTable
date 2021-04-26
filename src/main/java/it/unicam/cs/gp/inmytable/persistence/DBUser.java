package it.unicam.cs.gp.inmytable.persistence;

import com.google.common.hash.HashCode;
import it.unicam.cs.gp.inmytable.user.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUser extends DBConnection implements IDBUser{
    private String sql;
    private static List<User> users;


    public DBUser() throws SQLException {
        super();
        users = new ArrayList<>();
    }

    public DBUser(String connectionString, String username, String password) throws Exception{
        super(connectionString,username,password);
        users = new ArrayList<>();
    }

    @Override
    public void registerUser(User user) throws Exception {
        this.sql = "insert into user(Username, FirstName, LastName, Email, Password, FiscalCode, Id, Birth, Telephone, Address, Available) values (?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement prepStat = getConnection().prepareStatement(this.sql);
        prepStat.setString(1, user.getUsername());
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
        prepStat.executeUpdate();
        users.add(user);
    }

    @Override
    public List<User> getUsersList() throws Exception {
        if(users.isEmpty()) {
            String sql = "Select * from user";
            setData(sql);
            while (getData().next()) {
               // users.add(new User(getData().getString("Username"), getData().getString() );
            }
        }
        return users;
    }
}
