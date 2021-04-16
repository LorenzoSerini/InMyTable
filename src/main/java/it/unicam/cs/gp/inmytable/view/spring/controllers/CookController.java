package it.unicam.cs.gp.inmytable.view.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CookController {

    @GetMapping("/cook")
    public String getCook() { return "/cook"; }
}
