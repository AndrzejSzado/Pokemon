package app.webservice.pokemon.controller;

import app.webservice.pokemon.model.Card;
import app.webservice.pokemon.service.CardService;
import app.webservice.pokemon.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.*;

@Controller
public class BoosterController {

    private CardService cardService;
    private UserService userService;

    public BoosterController(CardService cardService, UserService userService) {
        this.cardService = cardService;
        this.userService = userService;
    }

    @GetMapping("/booster")
    public String getBoosterPage(Model model){
        model.addAttribute("logged", userService.isLogged());
        return "booster";
    }

    @PostMapping("/booster")
    public String openBoosterCards(Model model){
        List<Card> cards = cardService.openRandomBooster();
        model.addAttribute("cards",cards );
        return "redirect:/booster";
    }

}
