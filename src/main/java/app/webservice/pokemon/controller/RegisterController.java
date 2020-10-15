package app.webservice.pokemon.controller;

import app.webservice.pokemon.exceptions.UserServiceException;
import app.webservice.pokemon.request.UserRequest;
import app.webservice.pokemon.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        try {
            userService.save(userRequest);
        }catch (UserServiceException e){
//            e.printStackTrace();
            bindingResult.addError(new FieldError("user","email", e.getMessage()));
            return "register";
        }

        return "redirect:/";
    }
}
