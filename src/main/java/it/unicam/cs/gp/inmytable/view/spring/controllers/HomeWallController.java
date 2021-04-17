package it.unicam.cs.gp.inmytable.view.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeWallController {
    @GetMapping("/bacheca")
    public String getCook() { return "/bacheca"; }

}
