package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.user.IUser;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDB extends DBPersistence implements UserPersistence{
    private String sql;

    public UserDB() throws Exception {
        super();

    }

    public UserDB(String connectionString, String username, String password) throws Exception {
        super(connectionString,username,password);
    }

    @Override
    public void updateUser(IUser user, String email, int password, String city, String address, String id, String telephoneNumber, boolean available) throws SQLException {
        sql = "update User set Email=?, Password=?, City=?, Address=?, Id=?, telephone=?, Available=? where Username='"+user.getUsername()+"'";
        PreparedStatement prepStat = getConnection().prepareStatement(sql);
            prepStat.setString(1, email);
            prepStat.setInt(2, password);
            prepStat.setString(3, city);
            prepStat.setString(4, address);
            prepStat.setString(5, id);
            prepStat.setString(6, telephoneNumber);
            prepStat.setBoolean(7, available);
        prepStat.executeUpdate();
    }
}
