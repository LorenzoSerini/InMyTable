package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.user.User;

import java.util.List;

public interface IDBUser {

    /**
     * Register a new user
     * @param user a new User
     * @throws Exception
     */
    void registerUser(User user) throws Exception;

    /**
     *
     * @return users List
     * @throws Exception
     */
    List<User> getUsersList() throws Exception;
}
