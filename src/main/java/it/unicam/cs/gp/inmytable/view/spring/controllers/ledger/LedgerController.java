package it.unicam.cs.gp.inmytable.view.spring.controllers.ledger;

import it.unicam.cs.gp.inmytable.view.spring.controllers.BaseController;
import it.unicam.cs.gp.inmytable.view.spring.services.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LedgerController {
    @Autowired
    LedgerService ledgerService;

    @GetMapping("/storico")
    public String getLedger(Model model, HttpSession session){
        if(BaseController.isLoggedIn(session)){
            try {
                ledgerService.setLogUser(BaseController.getLogUser(session));
                model.addAttribute("publishedMeals", ledgerService.showPublishedMeals());
                model.addAttribute("publishedMealRequests", ledgerService.showPublishedMealRequests());
                model.addAttribute("notClosedMeals", ledgerService.showNotClosedAttendedMeals());
                model.addAttribute("closedMeals", ledgerService.showClosedAttendedMeals());
                return "/storico";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:/bacheca";
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
