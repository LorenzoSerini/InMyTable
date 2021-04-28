package it.unicam.cs.gp.inmytable.view.spring.controllers;

import it.unicam.cs.gp.inmytable.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class UserController {
    //TODO: DA ELIMINARE, SERVE SOLO PER ILLUSTRARE IL PROGETTO!
    private User deleteUser = new User("Johnny76", "john@example.com", "000 000000", "John", "Doe", "example".hashCode(), LocalDate.parse("1950-01-01"));

    @GetMapping("/profilo")
    public String getProfile(Model model) {
        model.addAttribute("user", deleteUser);
    return "/profilo";
    }
}



