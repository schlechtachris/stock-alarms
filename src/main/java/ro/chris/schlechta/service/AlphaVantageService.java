package ro.chris.schlechta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.chris.schlechta.model.BestMatches;
import ro.chris.schlechta.model.BestMatchesItem;
import ro.chris.schlechta.model.GlobalQuote;
import ro.chris.schlechta.model.GlobalQuoteItem;
import ro.chris.schlechta.model.persisted.Stock;
import ro.chris.schlechta.model.persisted.StockSymbol;
import ro.chris.schlechta.utils.Constants;

import java.util.*;

import static ro.chris.schlechta.utils.Constants.ASCII_ALPHABET_END_INDEX;
import static ro.chris.schlechta.utils.Constants.ASCII_ALPHABET_START_INDEX;

@Service
public class AlphaVantageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlphaVantageService.class);

    private final RestTemplate restTemplate;
    private final StockSymbolService stockSymbolService;
    private final StockService stockService;

    @Value("${alpha.vantage.api.key}")
    private String apiKey;

    @Autowired
    public AlphaVantageService(RestTemplate restTemplate, StockSymbolService stockSymbolService, StockService stockService) {
        this.restTemplate = restTemplate;
        this.stockSymbolService = stockSymbolService;
        this.stockService = stockService;
    }

    /**
     * fetch data for top 50 brands stocks
     *
     * @return {@link List} of {@link GlobalQuoteItem}
     */
    public List<Stock> fetchStocks() {
        LOGGER.info("Fetch stock values...");

        List<StockSymbol> symbols = fetchSymbols();
        List<Stock> stocks = stockService.getStocks();

        if (stocks.isEmpty() || stockIsNotUpToDate(stocks.get(0).getLastUpdate(), Calendar.getInstance().getTime())) {
            return persistOrUpdateStocks(symbols);
        }

        return stocks;
    }

    private boolean stockIsNotUpToDate(Date stockDate, Date now) {
        Calendar stockCalendar = Calendar.getInstance();
        stockCalendar.setTime(stockDate);

        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(now);

        return stockCalendar.get(Calendar.YEAR) < currentCalendar.get(Calendar.YEAR)
                || stockCalendar.get(Calendar.MONTH) < currentCalendar.get(Calendar.MONTH)
                || stockCalendar.get(Calendar.DAY_OF_MONTH) < currentCalendar.get(Calendar.DAY_OF_MONTH);
    }

    private List<Stock> persistOrUpdateStocks(List<StockSymbol> symbols) {
        List<GlobalQuoteItem> globalQuotes = new LinkedList<>();

        for (StockSymbol stockSymbol : symbols) {
            var url = String.format(Constants.ALPHA_VANTAGE_GLOBAL_QUOTE_PATH + "&symbol=%s&apikey=%s",
                    stockSymbol.getSymbol(), apiKey);

            LOGGER.info("Fetch stock data for symbol: {}", stockSymbol.getSymbol());
            globalQuotes.add(Objects.requireNonNull(restTemplate.getForEntity(url, GlobalQuote.class).getBody())
                    .getGlobalQuoteItem());
            waitBetweenCalls();
        }

        return stockService.saveOrUpdateStocks(globalQuotes);
    }

    private List<StockSymbol> fetchSymbols() {
        List<StockSymbol> stockSymbols = stockSymbolService.getAllSymbols();

        if (!stockSymbols.isEmpty()) {
            return stockSymbols;
        }

        List<BestMatchesItem> symbols = new LinkedList<>();

        for (int i = ASCII_ALPHABET_START_INDEX; i <= ASCII_ALPHABET_END_INDEX; i++) {
            char character = (char) i;
            var url = String.format(Constants.ALPHA_VANTAGE_SYMBOL_SEARCH_PATH + "&keywords=%c&apikey=%s",
                    character, apiKey);

            LOGGER.info("Search symbols for letter: {}", character);
            BestMatches bestMatches = restTemplate.getForEntity(url, BestMatches.class).getBody();

            if (bestMatches != null && bestMatches.getSymbols() != null) {
                symbols.addAll(bestMatches.getSymbols());
            }

            waitBetweenCalls();
        }

        return stockSymbolService.saveAllSymbols(symbols);
    }

    private void waitBetweenCalls() {
        try {
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            LOGGER.info("Waiting 1.1 second between calls to Alpha Vantage interrupted.");
        }
    }

}
