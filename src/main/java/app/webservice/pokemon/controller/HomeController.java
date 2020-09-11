package app.webservice.pokemon.controller;

import app.webservice.pokemon.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(Model model){
        model.addAttribute("statusInfo", userService.getStatus());
        model.addAttribute("logged", userService.isLogged());
        return "index";
    }

}
