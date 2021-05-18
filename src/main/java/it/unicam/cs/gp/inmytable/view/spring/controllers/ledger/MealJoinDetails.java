package it.unicam.cs.gp.inmytable.view.spring.controllers.ledger;

import it.unicam.cs.gp.inmytable.view.spring.controllers.BaseController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

public class MealJoinDetails {

    @GetMapping("/pasto-patecipato")
    public String getAllNotifications(Model model, HttpSession session){
        if(BaseController.isLoggedIn(session)){

            return "/pasto-partecipato";
        }
        return "/login";
    }
}
