package ro.chris.schlechta.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.chris.schlechta.dto.StockAlarmDto;
import ro.chris.schlechta.model.Stock;
import ro.chris.schlechta.model.StockAlarm;
import ro.chris.schlechta.model.User;
import ro.chris.schlechta.repository.StockAlarmRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockAlarmService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockAlarmService.class);

    private final StockAlarmRepository repository;
    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public StockAlarmService(StockAlarmRepository repository, UserService userService, EmailService emailService) {
        this.repository = repository;
        this.userService = userService;
        this.emailService = emailService;
    }

    public StockAlarm saveOrUpdateAlarm(StockAlarmDto stockAlarmDto) {
        User authenticatedUser = userService.getAuthenticatedUser();
        Optional<StockAlarm> existingAlarm = repository.findByStockSymbol(stockAlarmDto.getStockSymbol());

        if (existingAlarm.isPresent()) {
            if (existingAlarm.get().isActive()) {
                LOGGER.error("There is already an alarm defined for stock: {}.", stockAlarmDto.getStockSymbol());
                return null;
            } else {
                return updateExistingAlarm(stockAlarmDto, existingAlarm.get());
            }
        }

        StockAlarm newStockAlarm = new StockAlarm()
                .setStockSymbol(stockAlarmDto.getStockSymbol())
                .setInitialPrice(stockAlarmDto.getInitialPrice())
                .setCurrentPrice(stockAlarmDto.getCurrentPrice())
                .setPositiveVariance(stockAlarmDto.getPositiveVariance())
                .setNegativeVariance(stockAlarmDto.getNegativeVariance())
                .setActive(true)
                .setUser(authenticatedUser);

        return repository.save(newStockAlarm);
    }

    private StockAlarm updateExistingAlarm(StockAlarmDto stockAlarmDto, StockAlarm existingAlarm) {
        existingAlarm
                .setInitialPrice(stockAlarmDto.getInitialPrice())
                .setCurrentPrice(stockAlarmDto.getCurrentPrice())
                .setPositiveVariance(stockAlarmDto.getPositiveVariance())
                .setNegativeVariance(stockAlarmDto.getNegativeVariance())
                .setActive(true);

        return repository.save(existingAlarm);
    }

    public List<StockAlarm> getAllAlarmsPerUser() {
        User authenticatedUser = userService.getAuthenticatedUser();
        LOGGER.info("Retrieve all alarms for user {}", authenticatedUser.getEmail());

        return repository.findAllByUser(authenticatedUser);
    }

    public Optional<StockAlarm> getAlarmById(Long id) {
        LOGGER.info("Retrieve alarm by id: {}", id);

        return repository.findById(id);
    }

    public void updateAlarmsWithTheCurrentPrices(List<Stock> stocks) {
        LOGGER.info("Update current price for stock alarms");

        stocks
                .forEach(
                        stock -> {
                            Optional<StockAlarm> stockAlarm = repository.findByStockSymbol(stock.getStockSymbol());

                            stockAlarm.ifPresentOrElse(
                                    alarm -> {
                                        alarm.setCurrentPrice(stock.getPrice());
                                        repository.save(alarm);
                                    },
                                    () -> LOGGER.info("There is no alarm for stock: {}.", stock.getStockSymbol()));
                        });
    }

    public void deleteAllAlarms() {
        User authenticatedUser = userService.getAuthenticatedUser();
        LOGGER.info("Delete all alarms for user {}", authenticatedUser.getEmail());

        repository.deleteAllByUser(authenticatedUser);
    }

    public void verifyAlarms() {
        LOGGER.info("Verify active stock alarms...");

        List<StockAlarm> activeAlarms = repository.findAll()
                .stream()
                .filter(StockAlarm::isActive)
                .collect(Collectors.toList());

        if (activeAlarms.isEmpty()) {
            LOGGER.info("There are no active stock alarms defined...");
            return;
        }

        activeAlarms
                .forEach(
                        stockAlarm -> {
                            double priceVariationPercentage = calculatePriceVariation(stockAlarm);

                            if (priceVariationPercentage > stockAlarm.getPositiveVariance()
                                    || priceVariationPercentage < stockAlarm.getNegativeVariance()) {

                                emailService.sendEmail(stockAlarm);
                                stockAlarm.setActive(false);
                            }
                        }
                );

        repository.saveAll(activeAlarms);
        LOGGER.info("The users were notified and alarms deactivated.");
    }

    private double calculatePriceVariation(StockAlarm stockAlarm) {
        return (stockAlarm.getCurrentPrice() / stockAlarm.getInitialPrice()) * 10;
    }

}
