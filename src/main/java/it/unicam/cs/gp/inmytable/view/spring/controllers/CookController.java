package it.unicam.cs.gp.inmytable.view.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CookController {

    @GetMapping("/cucina")
    public String getCook() {return "/cucina";}

    @PostMapping("/cucina")
    public ModelAndView postRegistra(Model model,
                                     @RequestParam("description") String description,
                                     @RequestParam("cognome") String cognome,
                                     @RequestParam("email") String email,
                                     @RequestParam("password") String password,
                                     @RequestParam("tipoUtente") String tipoUtente) {
}
