package ro.chris.schlechta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.chris.schlechta.dto.GlobalQuoteItemDto;
import ro.chris.schlechta.model.Stock;
import ro.chris.schlechta.model.StockSymbol;
import ro.chris.schlechta.repository.StockRepository;

import java.util.Calendar;
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

    public Stock saveOrUpdateStock(GlobalQuoteItemDto globalQuoteItemDto, StockSymbol symbol) {
        Optional<Stock> existingStock = repository.findByStockSymbol(symbol.getSymbol());

        if (existingStock.isPresent()) {
            LOGGER.info("Updating stock with symbol {}.", globalQuoteItemDto.getSymbol());
            return updateStock(globalQuoteItemDto, existingStock.get());
        }

        LOGGER.info("Persist new stock with symbol: {}", symbol.getSymbol());
        return createNewStock(globalQuoteItemDto, symbol.getCompanyName());
    }

    private Stock createNewStock(GlobalQuoteItemDto globalQuoteItemDto,
                                 String companyName) {

        Stock newStock = new Stock()
                .setStockSymbol(globalQuoteItemDto.getSymbol())
                .setCompanyName(companyName)
                .setPrice(Double.parseDouble(globalQuoteItemDto.getPrice()))
                .setLastUpdate(Calendar.getInstance().getTime());

        return repository.save(newStock);
    }

    private Stock updateStock(GlobalQuoteItemDto globalQuoteItemDto, Stock existingStock) {
        existingStock.setPrice(Double.parseDouble(globalQuoteItemDto.getPrice()));
        existingStock.setLastUpdate(Calendar.getInstance().getTime());

        return repository.save(existingStock);
    }

    public Optional<Stock> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Stock> findBySymbol(String symbol) {
        return repository.findByStockSymbol(symbol);
    }
}
