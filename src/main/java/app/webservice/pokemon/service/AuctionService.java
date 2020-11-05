package app.webservice.pokemon.service;

import app.webservice.pokemon.model.AppUser;
import app.webservice.pokemon.model.Auction;
import app.webservice.pokemon.model.Card;
import app.webservice.pokemon.repository.AuctionRepository;
import app.webservice.pokemon.request.AuctionBuyRequest;
import app.webservice.pokemon.request.AuctionSellRequest;
import app.webservice.pokemon.view_model.AuctionPageViewModel;
import app.webservice.pokemon.view_model.AuctionViewModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public void createAuction(AuctionSellRequest auctionSellRequest){
        AppUser user = userService.getLoggedUserOrThrow();
        Card card = cardService.findById(auctionSellRequest.getCardId()).orElseThrow();
        user.removeCard(card, auctionSellRequest.getQuantity());
        userService.save(user);

        Auction auction = new Auction(card,
                auctionSellRequest.getPrice(),
                auctionSellRequest.getQuantity(),
                user.getId());
        save(auction);
    }

    public void save(Auction auction){
        auctionRepository.save(auction);
    }

    public List<Auction> findAll(){
        return auctionRepository.findAll();
    }

    public AuctionPageViewModel getAuctionPageData(){
        return AuctionPageViewModel.builder()
                .auctions(getAuctionsView())
                .buyerMoney(userService.getLoggedUserOrThrow().getMoney())
                .build();
    }
    private List<AuctionViewModel> getAuctionsView(){
        List<Auction> auctions = findAll();
        List<AuctionViewModel> auctionViewModels = new ArrayList<>();
        for (Auction auction : auctions) {
            AuctionViewModel auctionViewModel = AuctionViewModel.builder()
                    .auctionId(auction.getId())
                    .imageUrl(auction.getCard().getImageUrl())
                    .price(auction.getPrice())
                    .quantity(auction.getQuantity())
                    .username(userService.getUsername(auction.getUserId()))
                    .build();
            auctionViewModels.add(auctionViewModel);
        }
        return auctionViewModels;
    }

    public void buyAuction(AuctionBuyRequest request){
        Auction auction = auctionRepository
                .findById(request.getAuctionId()).orElseThrow(); //TODO AuctionServiceException with message
        //TODO validation money, quantity,

        int userId = auction.getUserId();
        AppUser seller = userService.getUserById(userId);
        userService.getLoggedUserOrThrow().pay(auction.getPrice());
        seller.add(auction.getPrice());
        userService.save(userService.getLoggedUserOrThrow());
        userService.save(seller);
        auctionRepository.delete(auction);
    }


}
