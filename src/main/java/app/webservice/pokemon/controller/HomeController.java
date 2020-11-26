package app.webservice.pokemon.controller;

import app.webservice.pokemon.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController extends BaseController{
    private UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(Model model, HttpSession session){
        redirectToHome(model, session);
        return "index";
    }

}
