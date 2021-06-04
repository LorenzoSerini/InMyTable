package it.unicam.cs.gp.inmytable.view.spring.controllers.homewall;

import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.homewalls.HomeWall;
import it.unicam.cs.gp.inmytable.user.User;
import it.unicam.cs.gp.inmytable.view.spring.controllers.BaseController;
import it.unicam.cs.gp.inmytable.view.spring.services.FeedbackService;
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

    /*@Autowired
    FeedbackService feedbackService;*/


    @GetMapping("/bacheca")
    public String getHomeWall(Model model, HttpSession session) {
        if (BaseController.isLoggedIn(session)) {
            try {
                //feedbackService.setLogUser(BaseController.getLogUser(session));
                homeWallService.setLogUser((BaseController.getLogUser(session)));
                notificationService.setLogUser(BaseController.getLogUser(session));
                model.addAttribute("allNotifications", notificationService.getAllNotifications());
              //  model.addAttribute("toUserAverage", feedbackService.getToFeedbacksAverage(BaseController.getLogUser(session)));

                model.addAttribute("pendingMealCatalog", homeWallService.getPendingMealCatalog());
                model.addAttribute("publicMealsRequestCatalog", homeWallService.getPendingMealRequestCatalog());

                model.addAttribute("mealsPlace", homeWallService.getMealsArray(homeWallService.getPendingMealCatalog()));
                model.addAttribute("mealRequestsPlace", homeWallService.getMealRequestsArray(homeWallService.getPendingMealRequestCatalog()));

                model.addAttribute("mealsDescription", homeWallService.getMealsDescriptionArray(homeWallService.getPendingMealCatalog()));
                model.addAttribute("mealsDate", homeWallService.getMealsDateTimeArray(homeWallService.getPendingMealCatalog()));
                model.addAttribute("mealsId", homeWallService.getMealsIdArray(homeWallService.getPendingMealCatalog()));


                model.addAttribute("mealRequestsDescription", homeWallService.getMealRequestsDescriptionArray(homeWallService.getPendingMealRequestCatalog()));
                model.addAttribute("mealRequestsDate", homeWallService.getMealRequestsDateTimeArray(homeWallService.getPendingMealRequestCatalog()));
                model.addAttribute("mealRequestsId", homeWallService.getMealRequestsIdArray(homeWallService.getPendingMealRequestCatalog()));


            } catch (Exception e) {
                e.printStackTrace();
            }
            return "bacheca";
        }
        return "login";
    }


}
