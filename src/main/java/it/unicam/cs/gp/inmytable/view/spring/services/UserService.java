package it.unicam.cs.gp.inmytable.view.spring.services;

import it.unicam.cs.gp.inmytable.controllers.UserController;
import it.unicam.cs.gp.inmytable.user.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserController userController;

    /**
     * used to set log user
     * @param logUser the log user
     * @throws Exception
     */
    public void setLogUser(User logUser) throws Exception {
      this.userController=new UserController(logUser);
    }

    /**
     * update user information
     * @param email email
     * @param password password
     * @param city city
     * @param address address
     * @param id id
     * @param telephoneNumber telephoneNumber
     * @param available available
     * @throws Exception
     */
    public void updateUserInformation(String email, String password, String city, String address, String id, String telephoneNumber, boolean available) throws Exception {
        if(email.equals("")) email=null;
        if(password.equals("")) password=null;
        if(city.equals("")) city=null;
        if(address.equals("")) address=null;
        if(id.equals("")) id=null;
        if(telephoneNumber.equals("")) telephoneNumber=null;
        this.userController.setUser(email,password,city,address,id,telephoneNumber, available);
    }
}
