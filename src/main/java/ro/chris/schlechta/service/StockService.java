package ro.chris.schlechta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.chris.schlechta.model.GlobalQuote;
import ro.chris.schlechta.model.Stock;
import ro.chris.schlechta.model.StockAlarm;
import ro.chris.schlechta.repository.StockRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);

    private final StockRepository repository;

    @Autowired
    public StockService(StockRepository repository) {
        this.repository = repository;
    }

    public List<Stock> getStocks() {
        LOGGER.info("Retrieve all stocks...");
        return repository.findAll();
    }

    public void saveOrUpdateStocks(List<GlobalQuote> globalQuotes) {
        LOGGER.info("Saving or updating stocks...");

        for (GlobalQuote globalQuote : globalQuotes) {
            Optional<Stock> existingStock = repository.findByStockSymbol(globalQuote.getSymbol());

            if (existingStock.isPresent()) {
                updateStock(globalQuote, existingStock.get());
            } else {
                saveNewStock(globalQuote);
            }
        }
    }

    private void saveNewStock(GlobalQuote globalQuote) {
        Stock newStock = new Stock();

        newStock
                .setStockSymbol(globalQuote.getSymbol())
                .setPrice(Double.parseDouble(globalQuote.getPrice()));

        repository.save(newStock);
    }

    private void updateStock(GlobalQuote globalQuote, Stock existingStock) {
        existingStock.setPrice(Double.parseDouble(globalQuote.getPrice()));
        repository.save(existingStock);
    }

}
