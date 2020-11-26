package app.webservice.pokemon.controller;

import app.webservice.pokemon.exceptions.AuctionServiceException;
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

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/market")
public class MarketController extends BaseController{
    private UserService userService;
    private AuctionService auctionService;

    public MarketController(UserService userService, AuctionService auctionService) {
        this.userService = userService;
        this.auctionService = auctionService;
    }

    @GetMapping("/sell")
    public String getSellPage(Model model, HttpSession session){
        Map<Card, Integer> cards = userService.getLoggedUserOrThrow().getCards();
        model.addAttribute("cards", cards);
        AuctionSellRequest auctionRequest = new AuctionSellRequest();
        model.addAttribute(auctionRequest);
        updateSessionData(session);
        return "market-sell";
    }

    @PostMapping("/sell")
    public String sellCard(@ModelAttribute("auctionRequest") AuctionSellRequest auctionSellRequest, BindingResult bindingResult, HttpSession session){
        if (bindingResult.hasErrors()){
            return "market-sell";
        }
        auctionService.createAuction(auctionSellRequest);
        updateSessionData(session);
        return "redirect:/market/sell";
    }

    @GetMapping("/buy")
    public String getBuyPage(Model model, HttpSession session){
        model.addAttribute("data",auctionService.getAuctionPageData());
        model.addAttribute("request", new AuctionBuyRequest());
        updateSessionData(session);
        return "market-buy";
    }

    @PostMapping("/buy")
    public String buyCard(@ModelAttribute("request") AuctionBuyRequest request, HttpSession session, Model model){
        try {
            auctionService.buyAuction(request);
        } catch (AuctionServiceException e) {
            return redirectToHome(e.getMessage(), MessageType.ERROR, model,session);
        }
        updateSessionData(session);
        return "redirect:/market/buy";
    }


}
