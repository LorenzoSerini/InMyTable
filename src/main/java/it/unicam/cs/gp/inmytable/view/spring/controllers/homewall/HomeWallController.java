package it.unicam.cs.gp.inmytable.view.spring.controllers.homewall;

import it.unicam.cs.gp.inmytable.homewalls.HomeWall;
import it.unicam.cs.gp.inmytable.view.spring.controllers.BaseController;
import it.unicam.cs.gp.inmytable.view.spring.services.HomeWallService;
import it.unicam.cs.gp.inmytable.view.spring.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeWallController {

    @Autowired
    HomeWallService homeWallService;

    @Autowired
    NotificationService notificationService;


    @GetMapping("/bacheca")
    public String getHomewall(Model model, HttpSession session) {
        if (BaseController.isLoggedIn(session)) {
            try {
                homeWallService.setLogUser((BaseController.getLogUser(session)));
                notificationService.setLogUser(BaseController.getLogUser(session));
                model.addAttribute("allNotifications", notificationService.getAllNotifications());
                model.addAttribute("pendingMealCatalog", homeWallService.getPendingMealCatalog());
                model.addAttribute("publicMealsRequestCatalog", homeWallService.getPendingMealRequestCatalog());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "bacheca";
        }
        return "login";
    }


}
