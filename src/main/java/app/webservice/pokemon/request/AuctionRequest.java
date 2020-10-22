package app.webservice.pokemon.request;

import app.webservice.pokemon.model.Card;

public class AuctionRequest {

    private Card card;
    private int quantity;
    private int price;

    public Card getCard() {
        return card;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "AuctionRequest{" +
                "card=" + card +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
