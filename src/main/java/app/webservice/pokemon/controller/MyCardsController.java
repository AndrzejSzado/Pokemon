package app.webservice.pokemon.controller;

import app.webservice.pokemon.model.Card;
import app.webservice.pokemon.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class MyCardsController {
    private UserService userService;

    public MyCardsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/my-cards")
    public String getMyCards(Model model){
        Map<Card, Integer> cards = userService.getLoggedUserOrThrow().getCards();
        model.addAttribute("cards", cards);

        return "my-cards";
    }
}
