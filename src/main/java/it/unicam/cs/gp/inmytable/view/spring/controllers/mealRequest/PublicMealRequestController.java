package it.unicam.cs.gp.inmytable.view.spring.controllers.mealRequest;

import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.view.spring.controllers.BaseController;
import it.unicam.cs.gp.inmytable.view.spring.services.HomeWallService;
import it.unicam.cs.gp.inmytable.view.spring.services.PublicMealRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class PublicMealRequestController {
    private MealRequest mealRequest;

    @Autowired
    PublicMealRequestService mealRequestService;
    @Autowired
    HomeWallService homeWallService;

    @GetMapping("/richiesta-pasto-pubblico")
    public String getPublicMealRequest(HttpSession session) {
        if (BaseController.isLoggedIn(session)) {
            return "richiesta-pasto-pubblico";
        }
        return "login";
    }

    @PostMapping("/richiesta-pasto-pubblico")
    public String postAPublicMealRequest(Model model,
                                               HttpSession session,
                                               @RequestParam("description") String description, @RequestParam("mealType") String mealType, @RequestParam("address") String address,
                                               @RequestParam("consummationType") String consummationType, @RequestParam("paymentType") String paymentType, @RequestParam("pym") String pym,
                                               @RequestParam("startTime") String startTime, @RequestParam("closedTime") String finishTime, @RequestParam("allergy") String allergy,
                                               @RequestParam("mealsNumber") int mealsNumber) {
        try {
            mealRequestService.setLogUser((BaseController.getLogUser(session)));
            mealRequestService.postAPublicMealRequest(description, mealType, consummationType, paymentType, startTime, finishTime, pym, address, allergy, mealsNumber);
            return "redirect:bacheca";
        } catch (Exception e) {
            e.printStackTrace();
            return "richiesta-pasto-pubblico";
        }
    }


    @GetMapping("/ricerca-richieste-pasti-pubbliche")
    public String getPublicMeals(Model model, HttpSession session) {
        if(BaseController.isLoggedIn(session)) {
            try {
                homeWallService.setLogUser(BaseController.getLogUser(session));
                model.addAttribute("publicMealsRequestCatalog", homeWallService.getPendingMealRequestCatalog());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "ricerca-richieste-pasti-pubbliche";
        }
        return "login";
    }


    @GetMapping("/accetta-richiesta-pasto-pubblico")
    public String acceptPublicMealRequest(Model model, HttpSession session, @RequestParam("mealRequest") String id) {
        if(BaseController.isLoggedIn(session)) {
            try {
                homeWallService.setLogUser(BaseController.getLogUser(session));
                mealRequestService.setLogUser(BaseController.getLogUser(session));
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.mealRequest = homeWallService.getAMealRequest(id);
            if(mealRequest!=null){
                model.addAttribute("mealRequest", mealRequest);
                model.addAttribute("accept", mealRequestService.canIAccept(BaseController.getLogUser(session), mealRequest));
                model.addAttribute("consummation", mealRequestService.getConsummationType(mealRequest));

                return "accetta-richiesta-pasto-pubblico";

            }else return "redirect:bacheca";
        }
        return "login";
    }


    @PostMapping("/accetta-richiesta-pasto-pubblico")
    public ModelAndView acceptPublicMealRequest(){
        try {
            mealRequestService.acceptPublicMealRequest(this.mealRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mealRequest=null;
        return new ModelAndView("redirect:bacheca");
    }



}
