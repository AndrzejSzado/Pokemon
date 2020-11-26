package app.webservice.pokemon.exceptions;

public class CardServiceException extends RuntimeException{
    public CardServiceException(String message) {
        super(message);
    }
}
