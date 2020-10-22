package app.webservice.pokemon.service;

import app.webservice.pokemon.model.AppUser;
import app.webservice.pokemon.model.Auction;
import app.webservice.pokemon.model.Card;
import app.webservice.pokemon.repository.AuctionRepository;
import app.webservice.pokemon.request.AuctionRequest;
import org.springframework.stereotype.Service;

@Service
public class AuctionService {

    private UserService userService;
    private CardService cardService;
    private AuctionRepository auctionRepository;

    public AuctionService(UserService userService, CardService cardService, AuctionRepository auctionRepository) {
        this.userService = userService;
        this.cardService = cardService;
        this.auctionRepository = auctionRepository;
    }

    public void createAuction(AuctionRequest auctionRequest){
        AppUser user = userService.getLoggedUserOrThrow();
        Card card = cardService.findById(auctionRequest.getCardId()).orElseThrow();
        user.removeCard(card,auctionRequest.getQuantity());
        userService.save(user);

        Auction auction = new Auction(card,
                auctionRequest.getPrice(),
                auctionRequest.getQuantity(),
                user.getId());
        save(auction);
    }

    public void save(Auction auction){
        auctionRepository.save(auction);
    }
}
