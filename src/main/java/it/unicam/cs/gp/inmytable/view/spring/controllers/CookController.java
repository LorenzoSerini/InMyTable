package it.unicam.cs.gp.inmytable.view.spring.controllers;

import it.unicam.cs.gp.inmytable.controllers.MealsController;
import it.unicam.cs.gp.inmytable.view.spring.services.CookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CookController {
    private MealsController mealsController;

    @Autowired
    CookService cookService;

    @GetMapping("/cucina")
    public String getCook() {
        return "/cucina";
    }


    @PostMapping("/cucina")
    public void postCook(Model model,
                         @RequestParam("description") String description, @RequestParam("mealType") String mealType, @RequestParam("ingredients") String ingredients,
                         @RequestParam("address") String address, @RequestParam("homeHownerEmail") String email, @RequestParam("homeHownerNumber") String hhownerNumber,
                         @RequestParam("consummationType") String consummationType, @RequestParam("paymentType") String paymentType, @RequestParam("pym") String pym,
                         @RequestParam("startTime") String startTime, @RequestParam("closedTime") String finishTime, @RequestParam("maxNumUsers") int maxNumUsers, @RequestParam("freeSubscription") String freeSubscription) {

        try {
            cookService.postAMeal(description, mealType,ingredients,address,email,hhownerNumber,consummationType,paymentType,pym,startTime,finishTime,maxNumUsers, Boolean.parseBoolean(freeSubscription));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println(mealType+"\n"+ingredients+"\n"+description+"\n"+address+"\n"+email+"\n"+hhownerNumber+"\n"+consummationType+"\n"+paymentType+"\n"+pym+"\n"+startTime+"\n"+finishTime);
    }


}
