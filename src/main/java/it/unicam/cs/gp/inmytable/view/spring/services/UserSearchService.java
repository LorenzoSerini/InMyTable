package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.allmeals.it.unicam.cs.gp.inmytable.mealrequest.PrivateMealRequest;
import it.unicam.cs.gp.inmytable.controllers.UserController;
import it.unicam.cs.gp.inmytable.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSearchService {
    private UserController userController;

    public void setLogUser(User logUser) throws Exception {
        this.userController = new UserController(logUser);
    }

    public List<User> getAvailableToRequestUsers(){
        List<User> availableUsers = new ArrayList<>();
        for(String key:this.userController.getAvailableToRequestUsers().keySet()){
            availableUsers.add(userController.getAvailableToRequestUsers().get(key));
        }
        return availableUsers;
    }
}
