package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.controllers.UserController;
import it.unicam.cs.gp.inmytable.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSearchService {
    private UserController userController;

    /**
     * used to set log user
     * @param logUser the log user
     * @throws Exception
     */
    public void setLogUser(User logUser) throws Exception {
        this.userController = new UserController(logUser);
    }

    /**
     * returns all available to request user
     * @return all available to request user
     * @throws Exception
     */
    public List<User> getAvailableToRequestUsers() throws Exception {
        List<User> availableUsers = new ArrayList<>();
        for(String key:this.userController.getAvailableToRequestUsers().keySet()){
            availableUsers.add(userController.getAvailableToRequestUsers().get(key));
        }
        return availableUsers;
    }
}
