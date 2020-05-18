package ro.chris.schlechta.service;

import org.eclipse.collections.impl.block.factory.HashingStrategies;
import org.eclipse.collections.impl.utility.ListIterate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.chris.schlechta.dto.BestMatchesItemDto;
import ro.chris.schlechta.model.StockSymbol;
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

    public List<StockSymbol> saveAllSymbols(List<BestMatchesItemDto> bestMatchesItemDtos) {
        LOGGER.info("Saving symbol details of stocks...");

        List<StockSymbol> stockSymbolDetails = ListIterate
                .distinct(bestMatchesItemDtos, HashingStrategies.fromFunction(BestMatchesItemDto::getSymbol))
                .stream()
                .map(bestMatchesItemDto -> new StockSymbol()
                        .setSymbol(bestMatchesItemDto.getSymbol())
                        .setCompanyName(bestMatchesItemDto.getName()))
                .collect(Collectors.toList());

        return repository.saveAll(stockSymbolDetails);
    }

    public List<StockSymbol> getAllSymbols() {
        LOGGER.info("Retrieve all stored stock symbols..");
        return repository.findAll();
    }

}
