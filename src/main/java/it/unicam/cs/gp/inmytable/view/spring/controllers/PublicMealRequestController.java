package it.unicam.cs.gp.inmytable.view.spring.controllers;

import it.unicam.cs.gp.inmytable.view.spring.services.MealRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PublicMealRequestController {

    @Autowired
    MealRequestService mealRequestService;

    @GetMapping("/richiesta-pasto-pubblico")
    public String getPublicMealRequest() {
        return "/richiesta-pasto-pubblico";
    }

    @PostMapping("/richiesta-pasto-pubblico")
    public void postAPublicMealRequest(Model model,
                         @RequestParam("description") String description, @RequestParam("mealType") String mealType, @RequestParam("address") String address,
                         @RequestParam("consummationType") String consummationType, @RequestParam("paymentType") String paymentType, @RequestParam("pym") String pym,
                         @RequestParam("startTime") String startTime, @RequestParam("closedTime") String finishTime,@RequestParam("allergy") String allergy,
                                       @RequestParam("mealsNumber") int mealsNumber) {
        try {
            mealRequestService.postAPublicMealRequest(description, mealType, consummationType, paymentType, startTime, finishTime, pym, address, allergy, mealsNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
