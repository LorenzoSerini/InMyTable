package it.unicam.cs.gp.inmytable.view.spring.controllers;

import it.unicam.cs.gp.inmytable.view.spring.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("/notifiche")
    public String getAllNotifications(Model model, HttpSession session){
        if(BaseController.isLoggedIn(session)){
            try {
                notificationService.setLogUser(BaseController.getLogUser(session));
                model.addAttribute("allNotifications", notificationService.getAllNotifications());
                return "/notifiche";
            } catch (Exception e) {
                e.printStackTrace();
                return "/bacheca";
            }
        }
        return "/login";
    }


    @GetMapping("/notifica")
    public String getANotification(Model model, HttpSession session, @RequestParam("notification") String id){
        if(BaseController.isLoggedIn(session)){
            try {
                notificationService.setLogUser(BaseController.getLogUser(session));

                model.addAttribute("allNotifications", notificationService.getAllNotifications());
                return "/notifiche";
            } catch (Exception e) {
                e.printStackTrace();
                return "/bacheca";
            }
        }
        return "/login";
    }



}
