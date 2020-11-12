package app.webservice.pokemon.service;


import app.webservice.pokemon.model.AppUser;
import app.webservice.pokemon.model.Card;
import app.webservice.pokemon.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CardService {
    private CardRepository cardRepository;
    private UserService userService;

    public CardService(CardRepository cardRepository, UserService userService) {
        this.cardRepository = cardRepository;
        this.userService = userService;
    }

    public List<Card> openRandomBooster(){
        AppUser loggedUser = userService.getLoggedUserOrThrow();
        Random random = new Random();
        List<Card> cards = cardRepository.findAll();
        List<Card> randomCards = new ArrayList<>();
        int randomNumber = random.nextInt(cards.size()); // to delete
        for (int i = 0; i < 5; i++) {
           // int randomNumber = random.nextInt(cards.size());
            Card card = cards.get(randomNumber);
            randomCards.add(card);
        }
        loggedUser.addCards(randomCards);
        userService.save(loggedUser);
        return randomCards;
    }

    public Optional<Card> findById(String id){
        return cardRepository.findById(id);
    }
}
