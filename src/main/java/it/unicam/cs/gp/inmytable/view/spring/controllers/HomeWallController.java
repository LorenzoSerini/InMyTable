package it.unicam.cs.gp.inmytable.view.spring.controllers;

import it.unicam.cs.gp.inmytable.homewalls.HomeWall;
import it.unicam.cs.gp.inmytable.view.spring.services.HomeWallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeWallController {

    @Autowired
    HomeWallService homeWallService;

    //TODO: IN FASE DI MODIFICA
    @GetMapping("/bacheca")
    public String getHomewall(Model model, HttpSession session) {
        if (BaseController.isLoggedIn(session)) {
            try {
                homeWallService.setLogUser((BaseController.getLogUser(session)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            model.addAttribute("pendingMealCatalog", homeWallService.getPendingMealCatalog());
            model.addAttribute("publicMealsRequestCatalog", homeWallService.getPendingMealRequestCatalog());
            return "/bacheca";
        }
        return "/login";
    }


}
