package ro.chris.schlechta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.chris.schlechta.model.BestMatchesItem;
import ro.chris.schlechta.model.persisted.StockSymbol;
import ro.chris.schlechta.repository.StockSymbolRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockSymbolService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockSymbolService.class);

    StockSymbolRepository repository;

    @Autowired
    public StockSymbolService(StockSymbolRepository repository) {
        this.repository = repository;
    }

    public List<StockSymbol> saveAllSymbols(List<BestMatchesItem> bestMatchesItems) {
        LOGGER.info("Saving symbol details of stocks...");

        List<StockSymbol> stockSymbolDetails = bestMatchesItems
                .stream()
                .map(bestMatchesItem -> new StockSymbol()
                        .setSymbol(bestMatchesItem.getSymbol())
                        .setCompanyName(bestMatchesItem.getName()))
                .collect(Collectors.toList());

        return repository.saveAll(stockSymbolDetails);
    }

    public List<StockSymbol> getAllSymbols() {
        LOGGER.info("Retrieve all stored stock symbols..");
        return repository.findAll();
    }

}
