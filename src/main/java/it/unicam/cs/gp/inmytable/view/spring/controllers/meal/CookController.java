package it.unicam.cs.gp.inmytable.view.spring.controllers.meal;

import it.unicam.cs.gp.inmytable.controllers.MealsController;
import it.unicam.cs.gp.inmytable.view.spring.controllers.BaseController;
import it.unicam.cs.gp.inmytable.view.spring.services.CookService;
import it.unicam.cs.gp.inmytable.view.spring.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class CookController {
    private MealsController mealsController;

     @Autowired()
    CookService cookService;

    @Autowired
    NotificationService notificationService;

    @GetMapping("/cucina")
    public String getCook(Model model, HttpSession session) {
        if (BaseController.isLoggedIn(session)) {
            try {
                notificationService.setLogUser(BaseController.getLogUser(session));
                model.addAttribute("allNotifications", notificationService.getAllNotifications());
                return "cucina";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:bacheca";
        }
        return "login";
    }


    @PostMapping("/cucina")
    public ModelAndView postCook(Model model,
                                 HttpSession session,
                                 @RequestParam("description") String description, @RequestParam("mealType") String mealType, @RequestParam("ingredients") String ingredients,  @RequestParam("city") String city,
                                 @RequestParam("address") String address, @RequestParam("consummationType") String consummationType, @RequestParam("paymentType") String paymentType, @RequestParam("pym") String pym,
                                 @RequestParam("startTime") String startTime, @RequestParam("closedTime") String finishTime, @RequestParam("maxNumUsers") int maxNumUsers, @RequestParam("freeSubscription") String freeSubscription) {

        try {
            cookService.setLogUser((BaseController.getLogUser(session)));
            cookService.postAMeal(description, mealType,ingredients,city,address, consummationType,paymentType,pym,startTime,finishTime,maxNumUsers, Boolean.parseBoolean(freeSubscription));
            return new ModelAndView("redirect:bacheca");
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("redirect:bacheca");
        }
    }

}
