package it.unicam.cs.gp.inmytable.view.spring.controllers;

import it.unicam.cs.gp.inmytable.controllers.MealsController;
import it.unicam.cs.gp.inmytable.view.spring.services.CookService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/cucina")
    public String getCook(HttpSession session) {
        if (BaseController.isLoggedIn(session)) {
            return "/cucina";
        }
        return "/login";
    }


    @PostMapping("/cucina")
    public ModelAndView postCook(Model model,
                                 HttpSession session,
                                 @RequestParam("description") String description, @RequestParam("mealType") String mealType, @RequestParam("ingredients") String ingredients,
                                 @RequestParam("address") String address, @RequestParam("consummationType") String consummationType, @RequestParam("paymentType") String paymentType, @RequestParam("pym") String pym,
                                 @RequestParam("startTime") String startTime, @RequestParam("closedTime") String finishTime, @RequestParam("maxNumUsers") int maxNumUsers, @RequestParam("freeSubscription") String freeSubscription) {

        try {
            cookService.setLogUser((BaseController.getLogUser(session)));
            cookService.postAMeal(description, mealType,ingredients,address, consummationType,paymentType,pym,startTime,finishTime,maxNumUsers, Boolean.parseBoolean(freeSubscription));
            return new ModelAndView("redirect:/bacheca");
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("redirect:/bacheca");
        }
    }

}
