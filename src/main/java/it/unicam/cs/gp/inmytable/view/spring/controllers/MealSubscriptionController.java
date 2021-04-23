package it.unicam.cs.gp.inmytable.view.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MealSubscriptionController {

    @GetMapping("/iscriviti-pasto")
    public String getMealSubscription(){return "/iscriviti-pasto";}
}
