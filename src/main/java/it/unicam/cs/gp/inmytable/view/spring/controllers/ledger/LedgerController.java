package it.unicam.cs.gp.inmytable.view.spring.controllers.ledger;

import it.unicam.cs.gp.inmytable.view.spring.controllers.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LedgerController {

    @GetMapping("/storico")
    public String getLedger(Model model, HttpSession session){
        if(BaseController.isLoggedIn(session)){

                return "/storico";
        }
        return "/login";
    }

    @GetMapping("/lista-pasti")
    public String getMealsList(Model model, HttpSession session){
        if(BaseController.isLoggedIn(session)){

            return "/lista-pasti";
        }
        return "/login";
    }

    @GetMapping("/lista-richieste")
    public String getMealRequestsList(Model model, HttpSession session){
        if(BaseController.isLoggedIn(session)){

            return "/lista-richieste";
        }
        return "/login";
    }

    @GetMapping("/lista-pasti-partecipati")
    public String getMealsJoinList(Model model, HttpSession session){
        if(BaseController.isLoggedIn(session)){

            return "/lista-pasti-partecipati";
        }
        return "/login";
    }
}
