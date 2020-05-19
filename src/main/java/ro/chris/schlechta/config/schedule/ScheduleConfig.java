package ro.chris.schlechta.config.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ro.chris.schlechta.model.Stock;
import ro.chris.schlechta.service.AlphaVantageService;
import ro.chris.schlechta.service.StockAlarmService;

import java.util.List;

@Configuration
@EnableScheduling
public class ScheduleConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleConfig.class);

    private final AlphaVantageService alphaVantageService;
    private final StockAlarmService stockAlarmService;

    @Autowired
    public ScheduleConfig(AlphaVantageService alphaVantageService,
                          StockAlarmService stockAlarmService) {

        this.alphaVantageService = alphaVantageService;
        this.stockAlarmService = stockAlarmService;
    }

    @Scheduled(fixedRateString = "${check.alarms.interval}", initialDelay = 1000)
    public void updateStocksAndVerifyAlarms() {
        LOGGER.info("----- Update stocks & verify alarms -----");

        List<Stock> stocks = alphaVantageService.fetchStocks();
        stockAlarmService.updateAlarmsWithTheCurrentPrices(stocks);
        stockAlarmService.verifyAlarms();
    }
}