package app.webservice.pokemon.view_model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuctionViewModel {
    private int auctionId;
    private String imageUrl;
    private int price;
    private int quantity;
    private String username;
}
