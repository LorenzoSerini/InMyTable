package it.unicam.cs.gp.inmytable.view.spring.services;



import it.unicam.cs.gp.inmytable.controllers.UserController;
import it.unicam.cs.gp.inmytable.user.User;
import org.springframework.stereotype.Service;

@Service
public class PrivateMealRequestService {
    private UserController userController;
    private User me;

    public void setLogUser(User logUser) throws Exception {
        userController = new UserController(logUser);
        this.me = logUser;
    }

    public User getUser(String username) throws Exception {
        return userController.getUser(username);
    }

    public boolean itIsMe(User requestTo){return requestTo.equals(me);}

}
