package it.unicam.cs.gp.inmytable.view.spring.controllers;

import it.unicam.cs.gp.inmytable.view.spring.services.HomeWallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class HomeWallController {

    @Autowired
    HomeWallService homeWallService;

    @GetMapping("/bacheca")
    public String getHomewall(Model model) {
       model.addAttribute("pendingMealCatalog", homeWallService.getPendingMealCatalog());
       model.addAttribute("publicMealsRequestCatalog", homeWallService.getPendingMealRequestCatalog());
        return "/bacheca";
    }

}
