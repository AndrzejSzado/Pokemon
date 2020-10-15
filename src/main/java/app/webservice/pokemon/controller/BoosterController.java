package app.webservice.pokemon.controller;

import app.webservice.pokemon.model.Card;
import app.webservice.pokemon.service.CardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.*;

@Controller
public class BoosterController {

    private CardService cardService;

    public BoosterController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/booster")
    public String getBoosterPage(){
        return "booster";
    }

    @PostMapping("/booster")
    public String openBoosterCards(Model model){
        List<Card> cards = cardService.openRandomBooster();
        model.addAttribute("cards",cards );
        return "booster";
    }

}
