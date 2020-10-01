package app.webservice.pokemon.client;
import app.webservice.pokemon.model.Card;

import java.util.*;

public class CardReceiver {
    private List<Card> cards;

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        return "CardReceiver{" +
                "cards=" + cards +
                '}';
    }
}
