package ro.chris.schlechta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.chris.schlechta.model.GlobalQuote;
import ro.chris.schlechta.utils.Constants;

import java.util.LinkedList;
import java.util.List;

@Service
public class AlphaVantageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlphaVantageService.class);

    private final RestTemplate restTemplate;

    @Value("${alpha.vantage.api.key}")
    private String apiKey;

    @Autowired
    public AlphaVantageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * fetch data for top 50 brands stocks
     *
     * @return {@link List} of {@link GlobalQuote}
     */
    public List<GlobalQuote> fetchTop50GlobalQuotes() {
        LOGGER.info("Fetch stock values of top 50 brands world wide.");

        List<GlobalQuote> currentStocks = new LinkedList<>();

        for (String brand : Constants.TOP_50_BRANDS) {
            var url = String.format(Constants.ALPHA_VANTAGE_GLOBAL_QUOTE_PATH + "&symbol=%s&apikey=%s", brand, apiKey);
            currentStocks.add(restTemplate.getForEntity(url, GlobalQuote.class).getBody());
        }

        return currentStocks;
    }

}
