package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.user.User;

import java.util.Map;

public interface Persistence {

    /**
     *A map with the username associated with each user
     * @return the username associated with each user
     * @throws Exception
     */
    Map<String, User> getUsers() throws Exception;
}
