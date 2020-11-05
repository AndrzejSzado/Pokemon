package app.webservice.pokemon.view_model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class AuctionPageViewModel {
    private List<AuctionViewModel> auctions;
    private int buyerMoney;
}
