package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.controllers.GuestController;
import it.unicam.cs.gp.inmytable.user.User;
import org.springframework.stereotype.Service;

@Service
public class GuestService {
    private GuestController guestController;

    public GuestService() throws Exception {
        this.guestController = new GuestController();
    }

    public User login(String username, String password) throws Exception {
        return guestController.logIn(username,password);
    }

}
