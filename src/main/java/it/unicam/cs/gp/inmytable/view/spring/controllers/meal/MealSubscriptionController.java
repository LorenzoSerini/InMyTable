package it.unicam.cs.gp.inmytable.view.spring.controllers.meal;

import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.view.spring.controllers.BaseController;
import it.unicam.cs.gp.inmytable.view.spring.services.HomeWallService;
import it.unicam.cs.gp.inmytable.view.spring.services.MealSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class MealSubscriptionController {
    @Autowired
    HomeWallService homeWallService;
    @Autowired
    MealSubscriptionService mealSubscriptionService;

    private Meal meal;


    @GetMapping("/iscriviti-pasto")
    public String getMealSubscription(Model model, HttpSession session, @RequestParam("meal") int hashCode){
        if (BaseController.isLoggedIn(session)) {
            try {
                homeWallService.setLogUser(BaseController.getLogUser(session));
                mealSubscriptionService.setLogUser(BaseController.getLogUser(session));
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.meal = homeWallService.getAMeal(hashCode);
            if (meal != null) {
                model.addAttribute("meal", meal);
                model.addAttribute("signUp", mealSubscriptionService.canISignUp(BaseController.getLogUser(session), meal));
                model.addAttribute("logUser", BaseController.getLogUser(session));
                model.addAttribute("subscription", mealSubscriptionService.getFreeSubscription(meal));
                model.addAttribute("consummation", mealSubscriptionService.getConsummationType(meal));
                return "/iscriviti-pasto";
            } else return "redirect:/bacheca";
        }
        return "/login";
    }

    @PostMapping("/iscriviti-pasto")
    public ModelAndView joinToMeal(HttpSession session){
        try {
            mealSubscriptionService.joinToMeal(meal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        meal=null;
        return new ModelAndView("redirect:/bacheca");
    }
}
