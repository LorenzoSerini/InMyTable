package it.unicam.cs.gp.inmytable.view.spring.controllers.user;

import com.google.common.hash.HashCode;
import it.unicam.cs.gp.inmytable.user.User;
import it.unicam.cs.gp.inmytable.view.spring.controllers.BaseController;
import it.unicam.cs.gp.inmytable.view.spring.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
public class UserController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("/profilo")
    public String getProfile(Model model, HttpSession session) {
        if (BaseController.isLoggedIn(session)) {
            try {
                notificationService.setLogUser(BaseController.getLogUser(session));
                model.addAttribute("allNotifications", notificationService.getAllNotifications());
                model.addAttribute("user", BaseController.getLogUser(session));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "profilo";
        }
        return "login";
    }


}



