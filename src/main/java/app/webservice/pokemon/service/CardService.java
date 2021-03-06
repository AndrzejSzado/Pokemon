package app.webservice.pokemon.service;


import app.webservice.pokemon.exceptions.CardServiceException;
import app.webservice.pokemon.model.AppUser;
import app.webservice.pokemon.model.Card;
import app.webservice.pokemon.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CardService {
    public final static int BOOSTER_COST = 100;
    private CardRepository cardRepository;
    private UserService userService;


    public CardService(CardRepository cardRepository, UserService userService) {
        this.cardRepository = cardRepository;
        this.userService = userService;
    }

    public List<Card> openRandomBooster(){
        AppUser loggedUser = userService.getLoggedUserOrThrow();
        Random random = new Random();

        if (loggedUser.getMoney() - BOOSTER_COST < 0){
            throw new CardServiceException("Not enough money to buy a booster");
        }

        List<Card> cards = cardRepository.findAll();
        List<Card> randomCards = new ArrayList<>();
        int randomNumber = random.nextInt(cards.size()); // to delete
        for (int i = 0; i < 5; i++) {
           // int randomNumber = random.nextInt(cards.size());
            Card card = cards.get(randomNumber);
            randomCards.add(card);
        }
        loggedUser.pay(BOOSTER_COST);
        loggedUser.addCards(randomCards);
        userService.save(loggedUser);
        return randomCards;
    }

    public Optional<Card> findById(String id){
        return cardRepository.findById(id);
    }
}
