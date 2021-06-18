package it.unicam.cs.gp.inmytable.view.spring.controllers.help;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelpController {

    @GetMapping("/come-funziona")
    public String getHelp() {
        return "come-funziona";
    }


    @GetMapping("/aiuto")
    public String getFAQ() {
        return "aiuto";
    }


}
