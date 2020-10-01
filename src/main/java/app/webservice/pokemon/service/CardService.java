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

    public List<Card> getRandomBooster(){

        Random random = new Random();
        List<Card> cards = cardRepository.findAll();
        List<Card> randomCards = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            int randomNumber = random.nextInt(cards.size());
            Card card = cards.get(randomNumber);
            randomCards.add(card);
        }
        return randomCards;
    }
}
