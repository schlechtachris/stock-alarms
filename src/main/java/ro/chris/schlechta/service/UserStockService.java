package ro.chris.schlechta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.chris.schlechta.model.Stock;
import ro.chris.schlechta.model.User;
import ro.chris.schlechta.model.UserStock;
import ro.chris.schlechta.repository.UserStockRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserStockService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserStockService.class);

    private final UserStockRepository repository;
    private final UserService userService;
    private final StockService stockService;

    @Autowired
    public UserStockService(UserStockRepository repository, UserService userService, StockService stockService) {
        this.repository = repository;
        this.userService = userService;
        this.stockService = stockService;
    }

    public UserStock save(Long id) {
        User authenticatedUser = userService.getAuthenticatedUser();
        UserStock newUserStock = new UserStock();
        Optional<Stock> stock = stockService.findById(id);

        if (stock.isEmpty()) {
            LOGGER.info("There is no stock to subscribe to!");
            return null;
        }

        newUserStock
                .setStock(stock.get())
                .setUser(authenticatedUser);

        return repository.save(newUserStock);
    }

    public List<Stock> getAllStocksSubscribedTo() {
        User authenticatedUser = userService.getAuthenticatedUser();

        return repository
                .findAllByUser(authenticatedUser)
                .stream()
                .map(UserStock::getStock)
                .collect(Collectors.toList());
    }

}
