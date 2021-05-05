package it.unicam.cs.gp.inmytable.controllers;


import it.unicam.cs.gp.inmytable.persistence.AuthenticationDB;
import it.unicam.cs.gp.inmytable.persistence.AuthenticationPersistence;
import it.unicam.cs.gp.inmytable.user.User;
import it.unicam.cs.gp.inmytable.utility.UsersUtilities;
import java.time.LocalDate;

public class GuestController {
    private UsersUtilities utility;
    private AuthenticationPersistence userPersistence;

    public GuestController(AuthenticationPersistence userPersistence) throws Exception {
        this.userPersistence=userPersistence;
        this.utility = UsersUtilities.getInstance(userPersistence.getUsers());
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
        User user = utility.getUser(username);
        if (user==null) throw new NullPointerException("The username not exist!");
        if (utility.checkPassword(user,password)) {
            return user;
        }
        else
            throw new IllegalArgumentException("Wrong Password!");
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
                       LocalDate birth, String id, String fiscalCode, String address, boolean availableToRequests) throws Exception {
        if (username == null || email == null || telephoneNumber == null || firstName == null || lastName == null ||
                password == null || birth == null) throw new NullPointerException("One of the parameter is null!");
        if (!utility.checkUsername(username)) throw new IllegalArgumentException("Username already exists!");
        if (!utility.checkEmail(email)) throw new IllegalArgumentException("Email already exists or is wrong!");
        if (!utility.checkId(id)) throw new IllegalArgumentException("Id already exists or is wrong!");
        if (!utility.checkFiscalCode(fiscalCode)) throw new IllegalArgumentException("FiscalCode already exists or is wrong!");
        if (!utility.checkBirth(birth)) throw new IllegalArgumentException("You are too young!");
        User user = new User(username, email, telephoneNumber, firstName, lastName, password.hashCode(), birth, id, fiscalCode, address, availableToRequests);
        utility.insertUser(user);
        userPersistence.registerUser(user);
        return user;
    }
}
