package app.webservice.pokemon.controller;

import app.webservice.pokemon.exceptions.CardServiceException;
import app.webservice.pokemon.model.Card;
import app.webservice.pokemon.service.CardService;
import app.webservice.pokemon.service.UserService;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class BoosterController extends BaseController {

    private CardService cardService;
    private UserService userService;

    public BoosterController(CardService cardService, UserService userService) {
        this.cardService = cardService;
        this.userService = userService;
    }

    @GetMapping("/booster")
    public String getBoosterPage(Model model,HttpSession session){
        model.addAttribute("cost",CardService.BOOSTER_COST);
        updateSessionData(session);
        return "booster";
    }

    @PostMapping("/booster")
    public String openBoosterCards(Model model, HttpSession session, RedirectAttributes redirect){
        List<Card> cards;
        try {
            cards = cardService.openRandomBooster();
        } catch (CardServiceException e) {
            return redirectToHome(e.getMessage(), MessageType.ERROR, model, session);
        }
        redirect.addFlashAttribute("cards",cards );
        return "redirect:/booster";
    }

}
