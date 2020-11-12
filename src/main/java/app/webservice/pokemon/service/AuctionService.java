package app.webservice.pokemon.service;

import app.webservice.pokemon.exceptions.AuctionServiceException;
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
                .findById(request.getAuctionId())
                .orElseThrow(() -> new AuctionServiceException("Auction does not exist"));

        int userId = auction.getUserId();
        AppUser seller = userService.getUserById(userId);
        AppUser loggedUser = userService.getLoggedUserOrThrow();
        int totalAmount = auction.getPrice() * request.getQuantity();
        System.out.println(loggedUser.getMoney());

        //TODO split if's
        if(cantBuy(request, auction, loggedUser, totalAmount)){
            throw new AuctionServiceException("Not enough money, or quantity too high");
        }

        if (areDifferentUsers(seller, loggedUser)){
            loggedUser.pay(totalAmount);
            seller.add(totalAmount);
            userService.save(seller);
        }

        loggedUser.addCard(auction.getCard(), auction.getQuantity());
        userService.save(loggedUser);
        auction.decreaseQuantity(request.getQuantity());

        if(auction.areNoCards()){
            auctionRepository.delete(auction);
        } else {
            save(auction);
        }
    }

    private boolean areDifferentUsers(AppUser seller, AppUser loggedUser) {
        return !loggedUser.equals(seller);
    }

    private boolean cantBuy(AuctionBuyRequest request, Auction auction, AppUser loggedUser, int totalAmount) {
        return loggedUser.getMoney() < totalAmount ||
                request.getQuantity() > auction.getQuantity();
    }
}
