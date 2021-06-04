package it.unicam.cs.gp.inmytable.view.spring.controllers.authentication;

import it.unicam.cs.gp.inmytable.view.spring.controllers.BaseController;
import it.unicam.cs.gp.inmytable.view.spring.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class AuthenticationController {

    @Autowired
    GuestService guestService;


    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegister() {return "register";}

    @PostMapping("/login")
    public ModelAndView doLogin(Model model, HttpSession session,
                                @RequestParam("username") String username,
                                @RequestParam("password") String password){
        try {
            model.addAttribute("error", false);
            BaseController.setLogUser(guestService.login(username, password), session);
            return new ModelAndView("redirect:bacheca");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", true);
        }

            return new ModelAndView("login");
    }


    @PostMapping("/register")
    public ModelAndView registration( HttpSession session,
                                      @RequestParam("username") String username, @RequestParam("email") String email,@RequestParam("telephone") String telephoneNumber,@RequestParam("firstName") String firstName,
                                      @RequestParam("lastName") String lastName,@RequestParam("password") String password,@RequestParam("birth") String birth,@RequestParam("id") String id,
                                      @RequestParam("fiscalCode") String fiscalCode,@RequestParam("city") String city,@RequestParam("address") String address,@RequestParam("available") boolean availableToRequests){
        try {
            BaseController.setLogUser(guestService.registration(username, email, telephoneNumber, firstName, lastName, password, birth, id, fiscalCode, city, address, availableToRequests), session);
            return new ModelAndView("redirect:bacheca");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("register");
    }


    @PostMapping("/logout")
    public ModelAndView doLogout(HttpSession session){
        try {
            BaseController.logout(session);
            return new ModelAndView("redirect:index");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("bacheca");
    }



}
