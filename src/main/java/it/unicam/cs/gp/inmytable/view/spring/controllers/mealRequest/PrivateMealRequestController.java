package it.unicam.cs.gp.inmytable.view.spring.controllers.mealRequest;

import it.unicam.cs.gp.inmytable.allmeals.MealStates;
import it.unicam.cs.gp.inmytable.user.User;
import it.unicam.cs.gp.inmytable.view.spring.controllers.BaseController;
import it.unicam.cs.gp.inmytable.view.spring.services.FeedbackService;
import it.unicam.cs.gp.inmytable.view.spring.services.LedgerService;
import it.unicam.cs.gp.inmytable.view.spring.services.PrivateMealRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class PrivateMealRequestController {
    private User requestTo;

    @Autowired
    PrivateMealRequestService privateMealRequestService;

    @Autowired
    LedgerService ledgerService;

    @Autowired
    FeedbackService feedbackService;

    @GetMapping("/profilo-utente")
    public String getUserProfile(Model model, HttpSession session, @RequestParam("user") String username){
        if (BaseController.isLoggedIn(session)) {
            try {
                privateMealRequestService.setLogUser(BaseController.getLogUser(session));
                feedbackService.setLogUser(BaseController.getLogUser(session));
                this.requestTo = privateMealRequestService.getUser(username);
                ledgerService.setLogUser(requestTo);
                model.addAttribute("publishedMeals", ledgerService.showPublishedMeals(p->p.getState().equals(MealStates.EXPIRED)).size());
                model.addAttribute("requestTo", requestTo);
                model.addAttribute("itIsMe", privateMealRequestService.itIsMe(requestTo));
                model.addAttribute("toUserAverage", feedbackService.getToFeedbacksAverage(requestTo));
                return "profilo-utente";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:bacheca";
        }
        return "login";
    }


    @GetMapping("/richiesta-pasto-privato")
    public String getPrivateMealRequest(Model model, HttpSession session){
        if (BaseController.isLoggedIn(session)) {
            try {
                privateMealRequestService.setLogUser(BaseController.getLogUser(session));
                model.addAttribute("requestTo", requestTo);
                return "richiesta-pasto-privato";
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "profilo-utente";
        }
        return "login";
    }


    @PostMapping("/richiesta-pasto-privato")
    public String postPrivateMealRequest(Model model,
                                         HttpSession session,
                                         @RequestParam("description") String description, @RequestParam("mealType") String mealType, @RequestParam("address") String address,
                                         @RequestParam("consummationType") String consummationType, @RequestParam("paymentType") String paymentType, @RequestParam("pym") String pym,
                                         @RequestParam("startTime") String startTime, @RequestParam("closedTime") String finishTime, @RequestParam("allergy") String allergy,
                                         @RequestParam("mealsNumber") int mealsNumber) {
        if (BaseController.isLoggedIn(session)) {
            try {
                privateMealRequestService.setLogUser((BaseController.getLogUser(session)));
                privateMealRequestService.postAPrivateMealRequest(requestTo,description, mealType, consummationType, paymentType, startTime, finishTime, pym, address, allergy, mealsNumber);
                requestTo=null;
                return "redirect:bacheca";
            } catch (Exception e) {
                e.printStackTrace();
                return "richiesta-pasto-pubblico";
            }
        }
        return "richiesta-pasto-pubblico";
    }

}
