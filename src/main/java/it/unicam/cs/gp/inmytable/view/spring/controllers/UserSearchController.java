package it.unicam.cs.gp.inmytable.view.spring.controllers;

import it.unicam.cs.gp.inmytable.view.spring.services.UserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserSearchController {

    @Autowired
    UserSearchService userSearchService;

    @GetMapping("/ricerca-utenti")
    public String getUserSearch(Model model, HttpSession session) {
        if (BaseController.isLoggedIn(session)) {
            try {
                userSearchService.setLogUser((BaseController.getLogUser(session)));
                model.addAttribute("availableToRequestUsers", userSearchService.getAvailableToRequestUsers());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "/ricerca-utenti";
        }
        return "/login";
    }

}
