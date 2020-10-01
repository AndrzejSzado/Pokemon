package app.webservice.pokemon.client;

import app.webservice.pokemon.repository.CardRepository;
import app.webservice.pokemon.service.CardService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
public class TCGRestClient {

    private static final String BASE_URL = "https://api.pokemontcg.io/v1/cards";
    private static final String PARAMETERS = "?page=%d&pageSize=%d";
    private static final int PAGE_SIZE = 1000;
    private RestTemplate restTemplate;
    private CardRepository cardRepository;

    public TCGRestClient(RestTemplate restTemplate, CardRepository cardRepository) {
        this.restTemplate = restTemplate;
        this.cardRepository = cardRepository;
    }

    @PostConstruct
    public void downloadAndSaveCards(){
        HttpHeaders httpHeaders = restTemplate.headForHeaders(BASE_URL);
        int maxPageNumber = Integer.parseInt(httpHeaders.get("Total-Count").get(0));
        if (cardRepository.count() == 0){
            int pageNumber = 1;
            while (pageNumber <= maxPageNumber/PAGE_SIZE+1){
                scheduleDownloadingPage(pageNumber);
                pageNumber++;
            }
        }
    }

    public void scheduleDownloadingPage(int pageNumber){
        Thread thread = new Thread(() -> {
            cardRepository.saveAll(((restTemplate.getForObject(String.format(BASE_URL+PARAMETERS,pageNumber,PAGE_SIZE), CardReceiver.class)).getCards()));
        });
        thread.start();
    }
}

