package app.webservice.pokemon.controller;

import app.webservice.pokemon.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController extends BaseController{

    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String getLoginPage(HttpSession session){
        updateSessionData(session);
        return "login";
    }

}
