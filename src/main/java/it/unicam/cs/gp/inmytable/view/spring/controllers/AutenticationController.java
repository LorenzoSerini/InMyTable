package it.unicam.cs.gp.inmytable.view.spring.controllers;

import it.unicam.cs.gp.inmytable.view.spring.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class AutenticationController {

    @Autowired
    GuestService guestService;

    @GetMapping("/login")
    public String getLogin() { return "/login";}

    @GetMapping("/register")
    public String getRegister() {return "/register";}

    @PostMapping("/login")
    public ModelAndView doLogin( HttpSession session,
            @RequestParam("username") String username,
                                 @RequestParam("password") String password){
        try {
            BaseController.setLogUser(guestService.login(username, password), session);
            return new ModelAndView("redirect:/bacheca");
        } catch (Exception e) {
            e.printStackTrace();
        }
            return new ModelAndView("/login");
    }


    @PostMapping("/logout")
    public ModelAndView doLogout(HttpSession session){
        try {
            BaseController.logout(session);
            return new ModelAndView("redirect:/index");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("/bacheca");
    }



}
