package it.unicam.cs.gp.inmytable.persistence;


import it.unicam.cs.gp.inmytable.user.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthenticationDB extends DBPersistence implements AuthenticationPersistence{
    private String sql;


    public AuthenticationDB() throws Exception {
        super();
    }

    public AuthenticationDB(String connectionString, String username, String password) throws Exception {
        super(connectionString,username,password);
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
        getUsers().put(user.getUsername(), user);
    }

}
