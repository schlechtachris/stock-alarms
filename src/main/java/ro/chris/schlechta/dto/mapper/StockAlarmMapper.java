package ro.chris.schlechta.dto.mapper;

import ro.chris.schlechta.dto.StockAlarmDto;
import ro.chris.schlechta.model.StockAlarm;

public class StockAlarmMapper {

    public static StockAlarmDto toStockAlarmDto(StockAlarm stockAlarm) {
        return new StockAlarmDto()
                .setStockSymbol(stockAlarm.getStockSymbol())
                .setInitialPrice(stockAlarm.getInitialPrice())
                .setCurrentPrice(stockAlarm.getCurrentPrice())
                .setPositiveVariance(stockAlarm.getPositiveVariance())
                .setNegativeVariance(stockAlarm.getNegativeVariance())
                .setActive(stockAlarm.isActive());
    }

}
