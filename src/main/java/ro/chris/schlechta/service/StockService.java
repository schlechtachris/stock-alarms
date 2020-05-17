package ro.chris.schlechta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.chris.schlechta.model.GlobalQuoteItem;
import ro.chris.schlechta.model.persisted.Stock;
import ro.chris.schlechta.repository.StockRepository;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<Stock> saveOrUpdateStocks(List<GlobalQuoteItem> globalQuoteItems) {
        LOGGER.info("Saving or updating stocks...");
        List<Stock> toBeSaved = new LinkedList<>();

        Map<String, Stock> stockMap = repository.findAll()
                .stream()
                .collect(Collectors.toMap(Stock::getStockSymbol, stock -> stock));

        for (GlobalQuoteItem globalQuoteItem : globalQuoteItems) {
            if (globalQuoteItem == null || globalQuoteItem.getSymbol() == null) {
                continue;
            }

            Stock existingStock = stockMap.get(globalQuoteItem.getSymbol());

            if (existingStock != null) {
                updateStock(toBeSaved, globalQuoteItem, existingStock);
            } else {
                createNewStock(toBeSaved, globalQuoteItem);
            }
        }

        return repository.saveAll(toBeSaved);
    }

    private void createNewStock(List<Stock> toBeSaved, GlobalQuoteItem globalQuoteItem) {
        toBeSaved.add(
                new Stock()
                        .setStockSymbol(globalQuoteItem.getSymbol())
                        .setPrice(Double.parseDouble(globalQuoteItem.getPrice()))
                        .setLastUpdate(Calendar.getInstance().getTime())
        );
    }

    private void updateStock(List<Stock> toBeSaved, GlobalQuoteItem globalQuoteItem, Stock existingStock) {
        existingStock.setPrice(Double.parseDouble(globalQuoteItem.getPrice()));
        existingStock.setLastUpdate(Calendar.getInstance().getTime());
        toBeSaved.add(existingStock);
    }

}
