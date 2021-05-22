package it.unicam.cs.gp.inmytable.controllers;


import it.unicam.cs.gp.inmytable.persistence.AuthenticationDB;
import it.unicam.cs.gp.inmytable.persistence.AuthenticationPersistence;
import it.unicam.cs.gp.inmytable.user.User;
import it.unicam.cs.gp.inmytable.utility.UsersUtilities;
import java.time.LocalDate;

public class GuestController {
    private AuthenticationPersistence userPersistence;

    public GuestController(AuthenticationPersistence userPersistence) throws Exception {
        this.userPersistence=userPersistence;
    }

    public GuestController() throws Exception {
        this(new AuthenticationDB());
    }

    /**
     * logIn
     * @param username  of the user
     * @param password  password of the user
     * @return the user
     * @throws Exception if the username not exists or the password is wrong
     */
    public User logIn(String username, String password) throws Exception{
        User user = userPersistence.getUsers().get(username);
        if (user==null) throw new NullPointerException("The username not exist!");
        if (UsersUtilities.checkPassword(user,password)) {
            return user;
        } else throw new IllegalArgumentException("Wrong Password!");
    }

    /**
     * SignIn
     * @param username  new username for the new user
     * @param email     new email for the new user
     * @param telephoneNumber   telephone number for the new user
     * @param firstName     his first name
     * @param lastName      his last name
     * @param password      his password
     * @param birth     his birthday
     * @param id    his id
     * @param fiscalCode    his fiscalCode
     * @return the new user
     * @throws Exception if one of the parameter is null, or one of email,username,fiscalCode,id already exist or it is wrong
     */
    public User signIn(String username, String email, String telephoneNumber, String firstName, String lastName, String password,
                       LocalDate birth, String id, String fiscalCode, String city, String address, boolean availableToRequests) throws Exception {
        if (username == null || email == null || telephoneNumber == null || firstName == null || lastName == null ||
                password == null || birth == null) throw new NullPointerException("One of the parameter is null!");
        if (userPersistence.getUsers().containsKey(username)) throw new IllegalArgumentException("Username already exists!");
        if (!UsersUtilities.checkEmail(userPersistence.getUsers(), email)) throw new IllegalArgumentException("Email already exists or is wrong!");
        if (!UsersUtilities.checkId(userPersistence.getUsers(), id)) throw new IllegalArgumentException("Id already exists or is wrong!");
        if (!UsersUtilities.checkFiscalCode(userPersistence.getUsers(), fiscalCode)) throw new IllegalArgumentException("FiscalCode already exists or is wrong!");
        if (!UsersUtilities.checkBirth(birth)) throw new IllegalArgumentException("You are too young!");

        User user = new User(username, email, telephoneNumber, firstName, lastName, password.hashCode(), birth, id, fiscalCode, city, address, availableToRequests);
        userPersistence.registerUser(user);
        return user;
    }

}
