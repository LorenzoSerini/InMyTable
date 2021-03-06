package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.controllers.GuestController;
import it.unicam.cs.gp.inmytable.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class GuestService {
    private GuestController guestController;

    public GuestService() throws Exception {
        this.guestController = new GuestController();
    }

    /**
     * used to login
     * @param username username
     * @param password password
     * @return log in user
     * @throws Exception
     */
    public User login(String username, String password) throws Exception {
        return guestController.logIn(username,password);
    }

    /**
     * used to register a user
     * @param username username
     * @param email email
     * @param telephoneNumber telephoneNumber
     * @param firstName firstName
     * @param lastName lastName
     * @param password password
     * @param birth birth
     * @param id id
     * @param fiscalCode fiscalCode
     * @param city city
     * @param address address
     * @param availableToRequests availableToRequests
     * @return register user
     * @throws Exception
     */
    public User registration(String username, String email, String telephoneNumber, String firstName, String lastName, String password,
                             String birth, String id, String fiscalCode, String city, String address, boolean availableToRequests) throws Exception {
        return guestController.signIn(username,email,telephoneNumber,firstName,lastName,password,LocalDate.parse(birth),id.toUpperCase(),fiscalCode.toUpperCase(),city,address,availableToRequests);
    }

}
