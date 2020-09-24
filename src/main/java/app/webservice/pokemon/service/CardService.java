package app.webservice.pokemon.service;


import app.webservice.pokemon.model.Card;
import app.webservice.pokemon.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.*;

import javax.annotation.PostConstruct;


@Service
public class CardService {
    private CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @PostConstruct
    public void addCard(){
        Card card = new Card("Pitchachu");
        Card card1 = new Card("Charizad");
        Card card2 = new Card("Bulbazaur");
        Card card3 = new Card("Snorlax");
        Card card4 = new Card("Voltorb");

        cardRepository.save(card);
        cardRepository.save(card1);
        cardRepository.save(card2);
        cardRepository.save(card3);
        cardRepository.save(card4);
    }

    public List<Card> getRandomBooster(){
        Random random = new Random();
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int randomNumber = random.nextInt((int)cardRepository.count())+1;
            Optional<Card> card = cardRepository.findById(randomNumber);
            card.ifPresent(c -> cards.add(c));
        }
        return cards;
    }
}
