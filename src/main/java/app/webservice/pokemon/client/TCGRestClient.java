package app.webservice.pokemon.client;

import app.webservice.pokemon.repository.CardRepository;
import app.webservice.pokemon.service.CardService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
public class TCGRestClient {

    private static final String URL = "https://api.pokemontcg.io/v1/";
    private RestTemplate restTemplate;
    private CardRepository cardRepository;

    public TCGRestClient(RestTemplate restTemplate, CardRepository cardRepository) {
        this.restTemplate = restTemplate;
        this.cardRepository = cardRepository;
    }

    @PostConstruct
    public void downloadAndSaveCards(){
        if (cardRepository.count() == 0){
            cardRepository.saveAll(((restTemplate.getForObject(URL+"cards", CardReceiver.class)).getCards()));
        }
    }
}

