package app.webservice.pokemon.controller;

import app.webservice.pokemon.request.UserRequest;
import app.webservice.pokemon.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import app.webservice.pokemon.model.User;

@Controller
public class RegisterController {
    private UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage(){
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(String email,String password){
        UserRequest userRequest = new UserRequest(email,password);
        userService.save(userRequest);
        return "redirect:/";
    }
}
