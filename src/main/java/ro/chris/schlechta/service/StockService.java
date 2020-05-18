package ro.chris.schlechta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.chris.schlechta.dto.GlobalQuoteItemDto;
import ro.chris.schlechta.model.Stock;
import ro.chris.schlechta.model.StockSymbol;
import ro.chris.schlechta.repository.StockRepository;

import java.util.*;
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

    public List<Stock> saveOrUpdateStocks(List<GlobalQuoteItemDto> globalQuoteItemDtos, List<StockSymbol> symbols) {
        LOGGER.info("Saving or updating stocks...");
        List<Stock> savedStocks = new LinkedList<>();

        Map<String, String> symbolsToCompanyName = symbols
                .stream()
                .collect(Collectors.toMap(StockSymbol::getSymbol, StockSymbol::getCompanyName));

        Map<String, Stock> stockMap = repository.findAll()
                .stream()
                .collect(Collectors.toMap(Stock::getStockSymbol, stock -> stock));

        for (GlobalQuoteItemDto globalQuoteItemDto : globalQuoteItemDtos) {
            if (globalQuoteItemDto == null || globalQuoteItemDto.getSymbol() == null) {
                continue;
            }

            Stock existingStock = stockMap.get(globalQuoteItemDto.getSymbol());

            if (existingStock != null) {
                savedStocks.add(updateStock(globalQuoteItemDto, existingStock));
            } else {
                savedStocks.add(createNewStock(globalQuoteItemDto, symbolsToCompanyName));
            }
        }

        return savedStocks;
    }

    private Stock createNewStock(GlobalQuoteItemDto globalQuoteItemDto,
                                 Map<String, String> symbolsAndCompany) {

        Stock newStock = new Stock()
                .setStockSymbol(globalQuoteItemDto.getSymbol())
                .setCompanyName(symbolsAndCompany.get(globalQuoteItemDto.getSymbol()))
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
}
