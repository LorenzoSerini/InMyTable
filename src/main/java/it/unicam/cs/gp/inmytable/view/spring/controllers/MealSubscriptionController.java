package it.unicam.cs.gp.inmytable.view.spring.controllers;

import com.google.common.hash.HashCode;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.view.spring.services.HomeWallService;
import it.unicam.cs.gp.inmytable.view.spring.services.MealRequestService;
import it.unicam.cs.gp.inmytable.view.spring.services.MealSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MealSubscriptionController {
    @Autowired
    HomeWallService homeWallService;
    @Autowired
    MealSubscriptionService mealSubscriptionService;


    @GetMapping("/iscriviti-pasto")
    public String getMealSubscription(Model model, @RequestParam("meal") int hashCode){
        Meal meal = homeWallService.getAMeal(hashCode);
        model.addAttribute("meal", meal);
        model.addAttribute("subscription",mealSubscriptionService.getFreeSubscription(meal));
        model.addAttribute("consummation",mealSubscriptionService.getConsummationType(meal));
        return "/iscriviti-pasto";}
}
