package it.unicam.cs.gp.inmytable.view.spring.controllers.ledger;

import it.unicam.cs.gp.inmytable.allmeals.mealrequest.IMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
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

@Controller
public class MealRequestDetailsController {

    @Autowired
    MealSubscriptionService mealSubscriptionService;

    @Autowired
    LedgerService ledgerService;

    @Autowired
    FeedbackService feedbackService;


    @GetMapping("/richiesta-pubblicata")
    public String getPublishedMealRequest(Model model, HttpSession session, @RequestParam("mealRequest") String mealRequestId){
        if(BaseController.isLoggedIn(session)){
            try {
                mealSubscriptionService.setLogUser(BaseController.getLogUser(session));
                ledgerService.setLogUser(BaseController.getLogUser(session));
                feedbackService.setLogUser(BaseController.getLogUser(session));

                IMealRequest mealRequest = ledgerService.getMealRequest(mealRequestId);
                model.addAttribute("mealRequest", mealRequest);
                model.addAttribute("consummation", mealSubscriptionService.getConsummationType(mealRequest));
                model.addAttribute("payment", mealSubscriptionService.getPayment(mealRequest));
                model.addAttribute("iSendFeedback", feedbackService.iSendFeedback(BaseController.getLogUser(session), mealRequest.getHomeOwner(), mealRequest));

                return "richiesta-pubblicata";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:bacheca";
        }
        return "login";
    }


    @GetMapping("/richiesta-ricevuta")
    public String getAttendedMealRequest(Model model, HttpSession session, @RequestParam("mealRequest") String mealRequestId){
        if(BaseController.isLoggedIn(session)){
            try {
                mealSubscriptionService.setLogUser(BaseController.getLogUser(session));
                ledgerService.setLogUser(BaseController.getLogUser(session));
                feedbackService.setLogUser(BaseController.getLogUser(session));

                IMealRequest mealRequest = ledgerService.getMealRequest(mealRequestId);
                model.addAttribute("mealRequest", mealRequest);
                model.addAttribute("consummation", mealSubscriptionService.getConsummationType(mealRequest));
                model.addAttribute("payment", mealSubscriptionService.getPayment(mealRequest));
                model.addAttribute("iSendFeedback", feedbackService.iSendFeedback(BaseController.getLogUser(session), mealRequest.getHost(), mealRequest));

                return "richiesta-ricevuta";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:bacheca";
        }
        return "login";
    }
}
