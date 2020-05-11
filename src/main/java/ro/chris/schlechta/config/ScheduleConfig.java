package ro.chris.schlechta.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ScheduleConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleConfig.class);

    //TODO implement logic in order that the system constantly scan stock prices and alert subscribers
}