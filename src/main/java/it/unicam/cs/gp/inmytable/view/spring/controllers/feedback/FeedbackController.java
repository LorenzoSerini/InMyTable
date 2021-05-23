package it.unicam.cs.gp.inmytable.view.spring.controllers.feedback;

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
                                 @RequestParam("user") String username, @RequestParam("rating") int rating, @RequestParam("comment") String comment) {
        if(BaseController.isLoggedIn(session)){
            try {
               feedbackService.setLogUser(BaseController.getLogUser(session));
                User to = feedbackService.getUser(username);
                feedbackService.postFeedback(to,rating,comment);
                return "redirect:storico";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:bacheca";
        }
        return "login";
    }
}
