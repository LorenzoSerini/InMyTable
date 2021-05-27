package it.unicam.cs.gp.inmytable.view.spring.controllers.feedback;

import it.unicam.cs.gp.inmytable.allmeals.Food;
import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.user.IUser;
import it.unicam.cs.gp.inmytable.user.User;
import it.unicam.cs.gp.inmytable.view.spring.controllers.BaseController;
import it.unicam.cs.gp.inmytable.view.spring.services.FeedbackService;
import it.unicam.cs.gp.inmytable.view.spring.services.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Map;

@Controller
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;


    @PostMapping("/invia-feedback")
    public String leaveFeedback(Model model,
                                 HttpSession session,
                                 @RequestParam("user") String username, @RequestParam("rating") int rating, @RequestParam("comment") String comment, @RequestParam("food") String foodId) {
        if(BaseController.isLoggedIn(session)){
            try {
               feedbackService.setLogUser(BaseController.getLogUser(session));
                User to = feedbackService.getUser(username);
                Food food = feedbackService.getFood(foodId);
                feedbackService.postFeedback(to,rating,comment, food);
                return "redirect:storico";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:bacheca";
        }
        return "login";
    }


    @GetMapping("/le-mie-recensioni")
    public String getFeedbacks(Model model, HttpSession session) {
        if(BaseController.isLoggedIn(session)){
            try {
                feedbackService.setLogUser(BaseController.getLogUser(session));
                model.addAttribute("toMe", feedbackService.getToFeedbacks(BaseController.getLogUser(session)));
                model.addAttribute("toMeSize", feedbackService.getToFeedbacks(BaseController.getLogUser(session)).size());
                model.addAttribute("toMeAverage", feedbackService.getToFeedbacksAverage(BaseController.getLogUser(session)));
                model.addAttribute("fromMe", feedbackService.getFromFeedbacks(BaseController.getLogUser(session)));
                model.addAttribute("fromMeSize", feedbackService.getToFeedbacks(BaseController.getLogUser(session)).size());
                model.addAttribute("fromMeAverage", feedbackService.getFromFeedbacksAverage(BaseController.getLogUser(session)));
                return "le-mie-recensioni";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:bacheca";
        }
        return "login";
    }


    @GetMapping("/recensioni-utente")
    public String getUserFeedbacks(Model model, HttpSession session, @RequestParam("user") String username) {
        if(BaseController.isLoggedIn(session)){
            try {
                feedbackService.setLogUser(BaseController.getLogUser(session));
                User to = feedbackService.getUser(username);
                model.addAttribute("toUser", feedbackService.getToFeedbacks(to));
                model.addAttribute("toUserSize", feedbackService.getToFeedbacks(to).size());
                model.addAttribute("toUserAverage", feedbackService.getToFeedbacksAverage(to));
                return "recensioni-utente";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:bacheca";
        }
        return "login";
    }

    @GetMapping("/recensione-inviata")
    public String getSendFeedback(Model model, HttpSession session, @RequestParam("feedback") String feedbackId) {
        if(BaseController.isLoggedIn(session)){
            try {
                feedbackService.setLogUser(BaseController.getLogUser(session));
                model.addAttribute("feedback", feedbackService.getFeedback(BaseController.getLogUser(session), feedbackId));
                return "recensione-inviata";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:bacheca";
        }
        return "login";
    }


    @GetMapping("/recensione-ricevuta")
    public String getReceivedFeedback(Model model, HttpSession session, @RequestParam("feedback") String feedbackId) {
        if(BaseController.isLoggedIn(session)){
            try {
                feedbackService.setLogUser(BaseController.getLogUser(session));
                model.addAttribute("feedback", feedbackService.getFeedback(BaseController.getLogUser(session), feedbackId));
                return "recensione-ricevuta";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:bacheca";
        }
        return "login";
    }
}
