package it.unicam.cs.gp.inmytable.view.spring.controllers;

import com.google.common.hash.HashCode;
import it.unicam.cs.gp.inmytable.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
public class UserController {


    @GetMapping("/profilo")
    public String getProfile(Model model, HttpSession session) {
        if (BaseController.isLoggedIn(session)) {
            model.addAttribute("user", BaseController.getLogUser(session));
            return "/profilo";
        }
        return "/login";
    }


}



