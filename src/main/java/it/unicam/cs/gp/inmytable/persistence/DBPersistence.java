package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.user.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public abstract class DBPersistence extends DBConnection implements Persistence{
    private String sql;
    private static Map<String, User> usersMap;


    public DBPersistence() throws Exception {
        super();
        usersMap = new HashMap<>();
        fillUsersMap();
    }

    public DBPersistence(String connectionString, String username, String password) throws Exception {
        super(connectionString,username,password);
        usersMap = new HashMap<>();
        fillUsersMap();
    }

    @Override
    public Map<String, User> getUsers(){
        return usersMap;
    }

    private Map<String, User> fillUsersMap() throws Exception {
        sql = "Select * from User";
        setData(sql);
        while (getData().next()) {
            if(!usersMap.containsKey(getData().getString("Username"))){
                User user = new User(getData().getString("Username"), getData().getString("Email"), getData().getString("Telephone"),
                        getData().getString("FirstName"), getData().getString("LastName"), getData().getInt("Password"),
                        LocalDate.parse(getData().getString("Birth")), getData().getString("FiscalCode"), getData().getString("Id"),
                        getData().getString("City"), getData().getString("Address"), getData().getBoolean("Available"));
                usersMap.put(user.getUsername(), user);
            }
        }
        return usersMap;
    }


}
