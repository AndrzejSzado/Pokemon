package app.webservice.pokemon.controller;

import app.webservice.pokemon.request.UserRequest;
import app.webservice.pokemon.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import app.webservice.pokemon.model.User;

@Controller
public class LoginController {

    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/log-in")
    public String getLoginPage(){
        return "login";
    }

    @PostMapping("/log-in")
    public String logIn(String email, String password){
        UserRequest user = new UserRequest(email, password);
        userService.login(user);
        return "redirect:/";
    }

}
