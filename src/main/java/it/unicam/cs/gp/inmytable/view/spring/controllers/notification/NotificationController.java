package it.unicam.cs.gp.inmytable.view.spring.controllers.notification;

import it.unicam.cs.gp.inmytable.allmeals.mealrequest.IMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
import it.unicam.cs.gp.inmytable.notification.SimpleNotification;
import it.unicam.cs.gp.inmytable.notification.SubscriptionNotification;
import it.unicam.cs.gp.inmytable.user.IUser;
import it.unicam.cs.gp.inmytable.view.spring.controllers.BaseController;
import it.unicam.cs.gp.inmytable.view.spring.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

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
                if(notificationService.isSimpleNotification(id)){
                    SimpleNotification<IUser> simpleNotification = notificationService.getSimpleNotification(id);
                    model.addAttribute("buttons", false);
                    model.addAttribute("notification", simpleNotification);
                    model.addAttribute("message","L'utente "+simpleNotification.from().getUsername()+" "+simpleNotification.getMsg());
                }else if(notificationService.isMealNotificationsWithPendingSubscription(id)){
                    SubscriptionNotification<IUser, IMeal> subscriptionNotification = notificationService.getMealSubscriptionNotifications(id);
                    model.addAttribute("buttons", true);
                    model.addAttribute("notification", subscriptionNotification);
                    model.addAttribute("message","L'utente "+subscriptionNotification.from().getUsername()+" "+subscriptionNotification.getMsg());
                }else if(notificationService.isMealNotificationsWithNoPendingSubscription(id)){
                    SubscriptionNotification<IUser, IMeal> subscriptionNotification = notificationService.getMealSubscriptionNotifications(id);
                    model.addAttribute("buttons", false);
                    model.addAttribute("notification", subscriptionNotification);
                    model.addAttribute("message","L'utente "+subscriptionNotification.from().getUsername()+" "+subscriptionNotification.getMsg());
                }else if(notificationService.isMealRequestNotificationsWithPendingSubscription(id)){
                    SubscriptionNotification<IUser, IMealRequest> subscriptionNotification = notificationService.getMealRequestSubscriptionNotifications(id);
                    model.addAttribute("buttons", true);
                    model.addAttribute("notification", subscriptionNotification);
                    model.addAttribute("message","L'utente "+subscriptionNotification.from().getUsername()+" "+subscriptionNotification.getMsg());
                }else if(notificationService.isMealRequestNotificationsWithNoPendingSubscription(id)){
                    SubscriptionNotification<IUser, IMealRequest> subscriptionNotification = notificationService.getMealRequestSubscriptionNotifications(id);
                    model.addAttribute("buttons", false);
                    model.addAttribute("notification", subscriptionNotification);
                    model.addAttribute("message","L'utente "+subscriptionNotification.from().getUsername()+" "+subscriptionNotification.getMsg());
                }
                return "/notifica";
            } catch (Exception e) {
                e.printStackTrace();
                return "/notifiche";
            }
        }
        return "/login";
    }


    @PostMapping("/notifica")
    public String setANotification(Model model, HttpSession session, @RequestParam("notification") String id){
        if(BaseController.isLoggedIn(session)){
            try {
                notificationService.setLogUser(BaseController.getLogUser(session));
                if(notificationService.isSimpleNotification(id)){
                    SimpleNotification<IUser> simpleNotification = notificationService.getSimpleNotification(id);
                    model.addAttribute("buttons", false);
                    model.addAttribute("notification", simpleNotification);
                    model.addAttribute("message","L'utente "+simpleNotification.from().getUsername()+" "+simpleNotification.getMsg());
                }else if(notificationService.isMealNotificationsWithPendingSubscription(id)){
                    SubscriptionNotification<IUser, IMeal> subscriptionNotification = notificationService.getMealSubscriptionNotifications(id);
                    model.addAttribute("buttons", true);
                    model.addAttribute("notification", subscriptionNotification);
                    model.addAttribute("message","L'utente "+subscriptionNotification.from().getUsername()+" "+subscriptionNotification.getMsg());
                }else if(notificationService.isMealNotificationsWithNoPendingSubscription(id)){
                    SubscriptionNotification<IUser, IMeal> subscriptionNotification = notificationService.getMealSubscriptionNotifications(id);
                    model.addAttribute("buttons", false);
                    model.addAttribute("notification", subscriptionNotification);
                    model.addAttribute("message","L'utente "+subscriptionNotification.from().getUsername()+" "+subscriptionNotification.getMsg());
                }else if(notificationService.isMealRequestNotificationsWithPendingSubscription(id)){
                    SubscriptionNotification<IUser, IMealRequest> subscriptionNotification = notificationService.getMealRequestSubscriptionNotifications(id);
                    model.addAttribute("buttons", true);
                    model.addAttribute("notification", subscriptionNotification);
                    model.addAttribute("message","L'utente "+subscriptionNotification.from().getUsername()+" "+subscriptionNotification.getMsg());
                }else if(notificationService.isMealRequestNotificationsWithNoPendingSubscription(id)){
                    SubscriptionNotification<IUser, IMealRequest> subscriptionNotification = notificationService.getMealRequestSubscriptionNotifications(id);
                    model.addAttribute("buttons", false);
                    model.addAttribute("notification", subscriptionNotification);
                    model.addAttribute("message","L'utente "+subscriptionNotification.from().getUsername()+" "+subscriptionNotification.getMsg());
                }
                return "/notifica";
            } catch (Exception e) {
                e.printStackTrace();
                return "/notifiche";
            }
        }
        return "/login";
    }



}
