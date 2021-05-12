package it.unicam.cs.gp.inmytable.view.spring.controllers;

import it.unicam.cs.gp.inmytable.user.User;
import it.unicam.cs.gp.inmytable.view.spring.services.PrivateMealRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class PrivateMealRequestController {
    private User requestTo;

    @Autowired
    PrivateMealRequestService privateMealRequestService;

    @GetMapping("/profilo-utente")
    public String getUserProfile(Model model, HttpSession session, @RequestParam("user") String username){
        if (BaseController.isLoggedIn(session)) {
            try {
                privateMealRequestService.setLogUser(BaseController.getLogUser(session));
                this.requestTo = privateMealRequestService.getUser(username);
            } catch (Exception e) {
                e.printStackTrace();
            }

            model.addAttribute("requestTo", requestTo);
            model.addAttribute("itIsMe", privateMealRequestService.itIsMe(requestTo));
            return "/profilo-utente";
        }
        return "/login";
    }
}
