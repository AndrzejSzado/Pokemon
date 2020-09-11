package app.webservice.pokemon.controller;

import app.webservice.pokemon.request.UserRequest;
import app.webservice.pokemon.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import app.webservice.pokemon.model.User;

import javax.validation.Valid;

@Controller
public class RegisterController {
    private UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        UserRequest userRequest = new UserRequest();
        model.addAttribute("user", userRequest);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserRequest userRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "register";
        }
        System.out.println(userRequest);
        userService.save(userRequest);
        return "redirect:/";
    }
}
