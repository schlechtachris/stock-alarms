package ro.chris.schlechta.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ro.chris.schlechta.StockAlarmsApplication;
import ro.chris.schlechta.dto.BestMatchesItemDto;
import ro.chris.schlechta.model.StockSymbol;
import ro.chris.schlechta.repository.StockSymbolRepository;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StockAlarmsApplication.class)
public class StockSymbolServiceTest {

    @MockBean
    StockSymbolRepository repository;

    @Autowired
    StockSymbolService stockSymbolService;

    @Test
    public void test_saveStockSymbols_duplicates() {
        Mockito.when(repository.saveAll(any())).thenReturn(null);
        ArgumentCaptor<List<StockSymbol>> stockSymbolsCaptor = ArgumentCaptor.forClass(List.class);

        stockSymbolService.saveAllSymbols(createBestMatchesItems());
        Mockito.verify(repository).saveAll(stockSymbolsCaptor.capture());

        Assert.assertEquals(1, stockSymbolsCaptor.getAllValues().size());
    }

    private List<BestMatchesItemDto> createBestMatchesItems() {
        return List.of(
                new BestMatchesItemDto()
                        .setSymbol("AMZN")
                        .setName("Amazon.com Inc."),
                new BestMatchesItemDto()
                        .setSymbol("AMZN")
                        .setName("Amazon.com Inc.")

        );
    }

}