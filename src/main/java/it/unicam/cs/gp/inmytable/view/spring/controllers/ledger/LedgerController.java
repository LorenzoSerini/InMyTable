package it.unicam.cs.gp.inmytable.view.spring.controllers.ledger;

import it.unicam.cs.gp.inmytable.allmeals.MealStates;
import it.unicam.cs.gp.inmytable.view.spring.controllers.BaseController;
import it.unicam.cs.gp.inmytable.view.spring.services.LedgerService;
import it.unicam.cs.gp.inmytable.view.spring.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LedgerController {
    @Autowired
    LedgerService ledgerService;

    @Autowired
    NotificationService notificationService;

    @GetMapping("/storico")
    public String getLedger(Model model, HttpSession session){
        if(BaseController.isLoggedIn(session)){
            try {
                ledgerService.setLogUser(BaseController.getLogUser(session));
                notificationService.setLogUser(BaseController.getLogUser(session));
                model.addAttribute("allNotifications", notificationService.getAllNotifications());
                model.addAttribute("publishedMeals", ledgerService.showPublishedMeals(p->p.getState().equals(MealStates.EXPIRED)));
                model.addAttribute("publishedMealRequests", ledgerService.showPublishedMealRequests(p->p.getState().equals(MealStates.EXPIRED)));
                model.addAttribute("answeredMealRequests", ledgerService.showAnsweredMealRequests(p->p.getState().equals(MealStates.EXPIRED)));
                model.addAttribute("closedMeals", ledgerService.showClosedAttendedMeals());
                return "storico";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:bacheca";
        }
        return "login";
    }



    @GetMapping("/i-miei-pasti")
    public String getPendingMeals(Model model, HttpSession session){
        if(BaseController.isLoggedIn(session)){
            try {
                ledgerService.setLogUser(BaseController.getLogUser(session));
                notificationService.setLogUser(BaseController.getLogUser(session));
                model.addAttribute("allNotifications", notificationService.getAllNotifications());
                model.addAttribute("publishedMeals", ledgerService.showPublishedMeals(p->!p.getState().equals(MealStates.EXPIRED)));
                model.addAttribute("notClosedMeals", ledgerService.showNotClosedAttendedMeals());
                return "i-miei-pasti";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:bacheca";
        }
        return "login";
    }

    @GetMapping("/le-mie-richieste")
    public String getPendingMealRequests(Model model, HttpSession session){
        if(BaseController.isLoggedIn(session)){
            try {
                ledgerService.setLogUser(BaseController.getLogUser(session));
                notificationService.setLogUser(BaseController.getLogUser(session));
                model.addAttribute("allNotifications", notificationService.getAllNotifications());
                model.addAttribute("publishedMealRequests", ledgerService.showPublishedMealRequests(p->!p.getState().equals(MealStates.EXPIRED)));
                model.addAttribute("answeredMealRequests", ledgerService.showAnsweredMealRequests(p->!p.getState().equals(MealStates.EXPIRED)));
                return "le-mie-richieste";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:bacheca";
        }
        return "login";
    }


}
