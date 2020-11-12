package app.webservice.pokemon.exceptions;

public class AuctionServiceException extends RuntimeException{
    public AuctionServiceException(String message) {
        super(message);
    }
}
