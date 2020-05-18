package ro.chris.schlechta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.chris.schlechta.dto.BestMatchesDto;
import ro.chris.schlechta.dto.BestMatchesItemDto;
import ro.chris.schlechta.dto.GlobalQuoteDto;
import ro.chris.schlechta.dto.GlobalQuoteItemDto;
import ro.chris.schlechta.model.Stock;
import ro.chris.schlechta.model.StockSymbol;
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
     * @return {@link List} of {@link GlobalQuoteItemDto}
     */
    public List<Stock> fetchStocks() {
        LOGGER.info("Fetch stock values...");

        List<StockSymbol> symbols = fetchSymbols();
        List<Stock> stocks = stockService.getStocks();

        if (stocks.isEmpty() || stockIsNotUpToDate(stocks.get(0).getLastUpdate(), Calendar.getInstance().getTime())) {
            return fetchStockDetails(symbols);
        }

//        if (!stocks.isEmpty()) {
//            stocks
//                    .stream()
//                    .filter(
//                            stock -> stockIsNotUpToDate(stock.getLastUpdate(),
//                                    Calendar.getInstance().getTime())
//                    ).map;
//        }

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

    private List<Stock> fetchStockDetails(List<StockSymbol> symbols) {
        List<GlobalQuoteItemDto> globalQuotes = new LinkedList<>();

        for (StockSymbol stockSymbol : symbols) {
            var url = String.format(Constants.ALPHA_VANTAGE_GLOBAL_QUOTE_PATH + "&symbol=%s&apikey=%s",
                    stockSymbol.getSymbol(), apiKey);

            LOGGER.info("Fetch stock data for symbol: {}", stockSymbol.getSymbol());
            globalQuotes.add(Objects.requireNonNull(restTemplate.getForEntity(url, GlobalQuoteDto.class).getBody())
                    .getGlobalQuoteItemDto());
            waitBetweenCalls();
        }

        return stockService.saveOrUpdateStocks(globalQuotes, symbols);
    }

    private List<StockSymbol> fetchSymbols() {
        List<StockSymbol> stockSymbols = stockSymbolService.getAllSymbols();

        if (!stockSymbols.isEmpty()) {
            return stockSymbols;
        }

        List<BestMatchesItemDto> symbols = new LinkedList<>();
        Set<BestMatchesItemDto> setSymbols = new HashSet<>();

        for (int i = ASCII_ALPHABET_START_INDEX; i <= ASCII_ALPHABET_END_INDEX; i++) {
            char character = (char) i;
            var url = String.format(Constants.ALPHA_VANTAGE_SYMBOL_SEARCH_PATH + "&keywords=%c&apikey=%s",
                    character, apiKey);

            LOGGER.info("Search symbols for letter: {}", character);
            BestMatchesDto bestMatchesDto = restTemplate.getForEntity(url, BestMatchesDto.class).getBody();

            if (bestMatchesDto != null && bestMatchesDto.getSymbols() != null) {
                symbols.addAll(bestMatchesDto.getSymbols());
                setSymbols.addAll(bestMatchesDto.getSymbols());
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
