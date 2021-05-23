package it.unicam.cs.gp.inmytable.view.spring.controllers.ledger;

import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
import it.unicam.cs.gp.inmytable.view.spring.controllers.BaseController;
import it.unicam.cs.gp.inmytable.view.spring.services.LedgerService;
import it.unicam.cs.gp.inmytable.view.spring.services.MealSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class MealDetailsController{

    @Autowired
    MealSubscriptionService mealSubscriptionService;

    @Autowired
    LedgerService ledgerService;

    @GetMapping("/dettagli-pasto")
    public String getAllNotifications(Model model, HttpSession session, @RequestParam("meal") String mealId){
        if(BaseController.isLoggedIn(session)){
            try {
                mealSubscriptionService.setLogUser(BaseController.getLogUser(session));
                ledgerService.setLogUser(BaseController.getLogUser(session));
                IMeal meal = ledgerService.getMeal(mealId);
                model.addAttribute("meal", meal);
                model.addAttribute("consummation", mealSubscriptionService.getConsummationType(meal));
                model.addAttribute("subscription", mealSubscriptionService.getFreeSubscription(meal));
                model.addAttribute("payment", mealSubscriptionService.getPayment(meal));
                return "dettagli-pasto";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:bacheca";
        }
        return "login";
    }
}
