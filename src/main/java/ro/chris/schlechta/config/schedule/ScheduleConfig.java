package ro.chris.schlechta.config.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ro.chris.schlechta.model.GlobalQuote;
import ro.chris.schlechta.service.AlphaVantageService;
import ro.chris.schlechta.service.StockAlarmService;
import ro.chris.schlechta.service.StockService;

import java.util.List;

@Configuration
@EnableScheduling
public class ScheduleConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleConfig.class);

    private final AlphaVantageService alphaVantageService;
    private final StockService stockService;
    private final StockAlarmService stockAlarmService;

    @Autowired
    public ScheduleConfig(AlphaVantageService alphaVantageService,
                          StockService stockService,
                          StockAlarmService stockAlarmService) {

        this.alphaVantageService = alphaVantageService;
        this.stockService = stockService;
        this.stockAlarmService = stockAlarmService;
    }

    @Scheduled(fixedRateString = "${check.alarms.interval}", initialDelay = 1000)
    public void updateStocksAndCheckAlarms() {
        LOGGER.info("----- Verify alarms -----");
        List<GlobalQuote> globalQuotes = alphaVantageService.fetchTop50GlobalQuotes();
        stockService.saveOrUpdateStocks(globalQuotes);
        stockAlarmService.updateCurrentPrice(globalQuotes);

        stockAlarmService.verifyAlarms();
    }
}