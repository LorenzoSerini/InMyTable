package it.unicam.cs.gp.inmytable.view.spring.controllers.ledger;

import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
import it.unicam.cs.gp.inmytable.user.IUser;
import it.unicam.cs.gp.inmytable.view.spring.controllers.BaseController;
import it.unicam.cs.gp.inmytable.view.spring.services.FeedbackService;
import it.unicam.cs.gp.inmytable.view.spring.services.LedgerService;
import it.unicam.cs.gp.inmytable.view.spring.services.MealSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Controller
public class MealDetailsController{

    @Autowired
    MealSubscriptionService mealSubscriptionService;

    @Autowired
    LedgerService ledgerService;

    @Autowired
    FeedbackService feedbackService;

    @GetMapping("/dettagli-pasto-cucinato")
    public String getPublishedMealDetail(Model model, HttpSession session, @RequestParam("meal") String mealId){
        if(BaseController.isLoggedIn(session)){
            try {
                mealSubscriptionService.setLogUser(BaseController.getLogUser(session));
                ledgerService.setLogUser(BaseController.getLogUser(session));
                feedbackService.setLogUser(BaseController.getLogUser(session));
                IMeal meal = ledgerService.getMeal(mealId);
                model.addAttribute("meal", meal);
                model.addAttribute("consummation", mealSubscriptionService.getConsummationType(meal));
                model.addAttribute("subscription", mealSubscriptionService.getFreeSubscription(meal));
                model.addAttribute("payment", mealSubscriptionService.getPayment(meal));
                model.addAttribute("iSendFeedback", feedbackService.iSendFeedback(BaseController.getLogUser(session), new ArrayList<>(meal.getUserList()), meal));
                return "dettagli-pasto-cucinato";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:bacheca";
        }
        return "login";
    }



    @GetMapping("/dettagli-pasto-partecipato")
    public String getJoinMealDetail(Model model, HttpSession session, @RequestParam("meal") String mealId){
        if(BaseController.isLoggedIn(session)){
            try {
                mealSubscriptionService.setLogUser(BaseController.getLogUser(session));
                ledgerService.setLogUser(BaseController.getLogUser(session));
                feedbackService.setLogUser(BaseController.getLogUser(session));
                IMeal meal = ledgerService.getMeal(mealId);
                model.addAttribute("meal", meal);
                model.addAttribute("consummation", mealSubscriptionService.getConsummationType(meal));
                model.addAttribute("subscription", mealSubscriptionService.getFreeSubscription(meal));
                model.addAttribute("payment", mealSubscriptionService.getPayment(meal));
                model.addAttribute("iSendFeedback", feedbackService.iSendFeedback(BaseController.getLogUser(session), meal.getHomeOwner(), meal));
                return "dettagli-pasto-partecipato";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:bacheca";
        }
        return "login";
    }

}
