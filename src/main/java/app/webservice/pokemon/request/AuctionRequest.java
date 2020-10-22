package app.webservice.pokemon.request;

import app.webservice.pokemon.model.Card;

public class AuctionRequest {

    private String cardId;
    private int quantity;
    private int price;

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    @Override
    public String toString() {
        return "AuctionRequest{" +
                "cardId='" + cardId + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
