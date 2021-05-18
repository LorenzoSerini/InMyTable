package it.unicam.cs.gp.inmytable.view.spring.controllers.user;

import it.unicam.cs.gp.inmytable.view.spring.controllers.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String getProfile(HttpSession session){
        if (BaseController.isLoggedIn(session)) {
            return "/profile";
        }
        return "/login";
    }
}
