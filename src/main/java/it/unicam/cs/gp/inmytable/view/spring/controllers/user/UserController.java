package it.unicam.cs.gp.inmytable.view.spring.controllers.user;

import com.google.common.hash.HashCode;
import it.unicam.cs.gp.inmytable.user.User;
import it.unicam.cs.gp.inmytable.view.spring.controllers.BaseController;
import it.unicam.cs.gp.inmytable.view.spring.services.NotificationService;
import it.unicam.cs.gp.inmytable.view.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
public class UserController {

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserService userService;

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



    @PostMapping("/profilo")
    public String setProfile(Model model, HttpSession session, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("city") String city,
                                   @RequestParam("address") String address, @RequestParam("id") String id, @RequestParam("telephoneNumber") String telephoneNumber, @RequestParam("available") boolean available) {
        if (BaseController.isLoggedIn(session)) {
            try {
                notificationService.setLogUser(BaseController.getLogUser(session));
                model.addAttribute("allNotifications", notificationService.getAllNotifications());
                model.addAttribute("user", BaseController.getLogUser(session));
               userService.setLogUser(BaseController.getLogUser(session));
                userService.updateUserInformation(email,password,city,address,id,telephoneNumber,available);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:profilo";
        }
        return "redirect:login";
    }


}



