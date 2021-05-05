package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.user.User;

import java.util.Map;

public interface AuthenticationPersistence extends Persistence{

    /**
     * Register a new user
     * @param user a new User
     * @throws Exception
     */
    void registerUser(User user) throws Exception;


}
