package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.user.User;

import java.util.List;
import java.util.Map;

public interface UserPersistence {

    /**
     * Register a new user
     * @param user a new User
     * @throws Exception
     */
    void registerUser(User user) throws Exception;

    /**
     *A map with the username associated with each user
     * @return the username associated with each user
     * @throws Exception
     */
    Map<String, User> getUsersMap() throws Exception;

}
