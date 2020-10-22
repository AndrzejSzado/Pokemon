package app.webservice.pokemon.controller;

import app.webservice.pokemon.model.Card;
import app.webservice.pokemon.request.AuctionRequest;
import app.webservice.pokemon.service.AuctionService;
import app.webservice.pokemon.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/market")
public class MarketController {
    private UserService userService;
    private AuctionService auctionService;

    public MarketController(UserService userService, AuctionService auctionService) {
        this.userService = userService;
        this.auctionService = auctionService;
    }

    @GetMapping("/sell")
    public String getSellPage(Model model){
        Map<Card, Integer> cards = userService.getLoggedUserOrThrow().getCards();
        model.addAttribute("cards", cards);
        AuctionRequest auctionRequest = new AuctionRequest();
        model.addAttribute(auctionRequest);
        return "market-sell";
    }

    @PostMapping("/sell")
    public String sellCard(@ModelAttribute("auctionRequest") AuctionRequest auctionRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "market-sell";
        }
        auctionService.createAuction(auctionRequest);
        System.out.println(auctionRequest);

        return "redirect:/market/sell";
    }

    @GetMapping("/buy")
    public String getBuyPage(){
        return "market-buy";
    }



}
