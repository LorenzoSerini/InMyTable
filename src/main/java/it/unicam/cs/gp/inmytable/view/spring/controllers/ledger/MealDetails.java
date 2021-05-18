package it.unicam.cs.gp.inmytable.view.spring.controllers.ledger;

import it.unicam.cs.gp.inmytable.view.spring.controllers.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MealDetails {

    @GetMapping("/dettagli-pasto")
    public String getAllNotifications(Model model, HttpSession session){
        if(BaseController.isLoggedIn(session)){

            return "/dettagli-pasto";
        }
        return "/login";
    }
}
