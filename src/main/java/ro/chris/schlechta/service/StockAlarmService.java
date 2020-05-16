package ro.chris.schlechta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.chris.schlechta.model.GlobalQuote;
import ro.chris.schlechta.model.StockAlarm;
import ro.chris.schlechta.model.User;
import ro.chris.schlechta.repository.StockAlarmRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StockAlarmService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockAlarmService.class);

    private final StockAlarmRepository repository;
    private final UserService userService;

    @Autowired
    public StockAlarmService(StockAlarmRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public StockAlarm createNewAlarm(StockAlarm stockAlarm) {
        Optional<StockAlarm> existingAlarm = repository.findByStockSymbol(stockAlarm.getStockSymbol());

        if (existingAlarm.isPresent() && existingAlarm.get().isActive()) {
            LOGGER.error("There is already an alarm defined for stock: {}", stockAlarm.getStockSymbol());
            return null;
        }

        return repository.save(stockAlarm);
    }

    public List<StockAlarm> getAllAlarmsPerUser() {
        User authenticatedUser = userService.getAuthenticatedUser();
        LOGGER.info("Retrieve all alarms for user {}", authenticatedUser.getEmail());

        return repository.findByUser(authenticatedUser);
    }

    public StockAlarm updateAlarm(StockAlarm alarm) {
        LOGGER.info("Update alarm for stock {}", alarm.getStockSymbol());

        return repository.save(alarm);
    }

    public void updateCurrentPrice(List<GlobalQuote> globalQuotes) {
        LOGGER.info("Update current price for stock alarms");

        globalQuotes
                .forEach(globalQuote -> {
                    Optional<StockAlarm> stockAlarm = repository.findByStockSymbol(globalQuote.getSymbol());
                    stockAlarm.get().setCurrentPrice(Double.parseDouble(globalQuote.getPrice()));
                    repository.save(stockAlarm.get());
                });
    }

    public void deleteAllAlarms() {
        User authenticatedUser = userService.getAuthenticatedUser();
        LOGGER.info("Delete all alarms for user {}", authenticatedUser.getEmail());

        repository.deleteAllByUser(authenticatedUser);
    }

    public void verifyAlarms() {
        List<StockAlarm> stockAlarms = repository.findAll();

        stockAlarms.forEach(stockAlarm -> {
            double priceVariationPercentage = calculatePriceVariation(stockAlarm);

            if (priceVariationPercentage > stockAlarm.getPositiveVariance()
                    || priceVariationPercentage < stockAlarm.getNegativeVariance()) {
                //TODO send email to users
            }
        });
    }

    private double calculatePriceVariation(StockAlarm stockAlarm) {
        return (stockAlarm.getCurrentPrice() / stockAlarm.getInitialPrice()) * 10;
    }

}
