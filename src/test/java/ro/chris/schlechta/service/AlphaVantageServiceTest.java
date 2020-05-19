package ro.chris.schlechta.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import ro.chris.schlechta.StockAlarmsApplication;
import ro.chris.schlechta.model.Stock;
import ro.chris.schlechta.model.StockSymbol;
import ro.chris.schlechta.repository.StockRepository;
import ro.chris.schlechta.repository.StockSymbolRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StockAlarmsApplication.class)
public class AlphaVantageServiceTest {

    @MockBean
    StockSymbolRepository stockSymbolRepository;

    @MockBean
    StockRepository stockRepository;

    @MockBean
    RestTemplate restTemplate;

    @Autowired
    StockSymbolService stockSymbolService;

    @Autowired
    StockService stockService;

    @Autowired
    AlphaVantageService alphaVantageService;

    @Test
    public void test_fetchStocks_alreadyExistingStocksAndSymbols() {
        Mockito.when(stockSymbolRepository.findAll()).thenReturn(createListOfStockSymbols());
        Mockito.when(stockRepository.findAll()).thenReturn(createListOfStocks());

        Assert.assertEquals(2, alphaVantageService.fetchStocks().size());
        Assert.assertEquals("V", alphaVantageService.fetchStocks().get(0).getStockSymbol());
        Assert.assertEquals("AMZN", alphaVantageService.fetchStocks().get(1).getStockSymbol());
    }

    private List<Stock> createListOfStocks() {
        return List.of(
                new Stock()
                .setId(1L)
                .setStockSymbol("V")
                .setCompanyName("Visa Inc.")
                .setPrice(191.38)
                .setLastUpdate(Date.from(Instant.now())),
                new Stock()
                        .setId(2L)
                        .setStockSymbol("AMZN")
                        .setCompanyName("Amazon.com Inc.")
                        .setPrice(2426.26)
                        .setLastUpdate(Date.from(Instant.now()))
        );
    }

    private List<StockSymbol> createListOfStockSymbols() {
        return List.of(
                new StockSymbol()
                        .setId(1L)
                        .setSymbol("V")
                        .setCompanyName("Visa Inc."),
                new StockSymbol()
                        .setId(2L)
                        .setSymbol("AMZ")
                        .setCompanyName("Amazon.com Inc."),
                new StockSymbol()
                        .setId(3L)
                        .setSymbol("AMZ")
                        .setCompanyName("Amazon.com Inc.")
        );
    }

}