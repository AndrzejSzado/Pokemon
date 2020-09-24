package app.webservice.pokemon.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TCGRestClient {

    private static final String URL = "https://api.pokemontcg.io/v1/";
    private RestTemplate restTemplate;

    public TCGRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
