package app.webservice.pokemon.controller;

import app.webservice.pokemon.model.Card;
import app.webservice.pokemon.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class MyCardsController extends BaseController{
    private UserService userService;

    public MyCardsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/my-cards")
    public String getMyCards(Model model, HttpSession session){
        Map<Card, Integer> cards = userService.getLoggedUserOrThrow().getCards();
        model.addAttribute("cards", cards);
        updateSessionData(session);
        return "my-cards";
    }
}
