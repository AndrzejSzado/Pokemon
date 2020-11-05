package app.webservice.pokemon.controller;

import app.webservice.pokemon.model.Auction;
import app.webservice.pokemon.model.Card;
import app.webservice.pokemon.request.AuctionBuyRequest;
import app.webservice.pokemon.request.AuctionSellRequest;
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
        model.addAttribute("logged", userService.isLogged());
        AuctionSellRequest auctionRequest = new AuctionSellRequest();
        model.addAttribute(auctionRequest);
        return "market-sell";
    }

    @PostMapping("/sell")
    public String sellCard(@ModelAttribute("auctionRequest") AuctionSellRequest auctionSellRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "market-sell";
        }
        auctionService.createAuction(auctionSellRequest);


        return "redirect:/market/sell";
    }

    @GetMapping("/buy")
    public String getBuyPage(Model model){
        model.addAttribute("logged", userService.isLogged());
        model.addAttribute("data",auctionService.getAuctionPageData());
        model.addAttribute("request", new AuctionBuyRequest());
        return "market-buy";
    }

    @PostMapping("/buy")
    public String buyCard(@ModelAttribute("request") AuctionBuyRequest request){
        System.out.println(request);
        return "redirect:/market/buy";
    }


}
