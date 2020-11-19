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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class RegisterController extends BaseController{
    private UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model, HttpSession session){
        UserRequest userRequest = new UserRequest();
        model.addAttribute("appUser", userRequest);
        updateSessionData(session);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("appUser") UserRequest userRequest, BindingResult bindingResult, HttpSession session){
        if (bindingResult.hasErrors()){
            return "register";
        }
        try {
            userService.register(userRequest);
        }catch (UserServiceException e){
//            e.printStackTrace();
            bindingResult.addError(new FieldError("appUser","email", e.getMessage()));
            return "register";
        }
        updateSessionData(session);
        return "redirect:/";
    }
}
