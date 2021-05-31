package it.unicam.cs.gp.inmytable.view.spring.controllers.user;

import it.unicam.cs.gp.inmytable.view.spring.controllers.BaseController;
import it.unicam.cs.gp.inmytable.view.spring.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("/profile")
    public String getProfile(Model model, HttpSession session){
        if (BaseController.isLoggedIn(session)) {
            try {
                notificationService.setLogUser(BaseController.getLogUser(session));
                model.addAttribute("allNotifications", notificationService.getAllNotifications());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "profile";
        }
        return "login";
    }
}
