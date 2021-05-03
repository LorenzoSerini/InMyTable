package it.unicam.cs.gp.inmytable.persistence;

import com.google.common.hash.HashCode;
import it.unicam.cs.gp.inmytable.user.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationDB extends DBConnection implements AuthenticationPersistence{
    private String sql;
    private Map<String, User> usersMap;


    public AuthenticationDB() throws SQLException {
        super();
        usersMap = new HashMap<>();
    }

    public AuthenticationDB(String connectionString, String username, String password) throws SQLException{
        super(connectionString,username,password);
        usersMap = new HashMap<>();
    }

    @Override
    public void registerUser(User user) throws SQLException {
        this.sql = "insert into User(Username, FirstName, LastName, Email, Password, FiscalCode, Id, Birth, Telephone, Address, Available) values (?,?,?,?,?,?,?,?,?,?,?)";
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
    }


    @Override
    public Map<String, User> getUsersMap() throws SQLException {
            String sql = "Select * from User";
            setData(sql);
            while (getData().next()) {
                if(!usersMap.containsKey(getData().getString("Username"))){
                    User user = new User(getData().getString("Username"), getData().getString("Email"), getData().getString("Telephone"),
                            getData().getString("FirstName"), getData().getString("LastName"), getData().getInt("Password"),
                            LocalDate.parse(getData().getString("Birth")), getData().getString("FiscalCode"), getData().getString("Id"),
                            getData().getString("Address"), getData().getBoolean("Available"));
                    usersMap.put(user.getUsername(), user);
                }
            }
        return usersMap;
    }
}
