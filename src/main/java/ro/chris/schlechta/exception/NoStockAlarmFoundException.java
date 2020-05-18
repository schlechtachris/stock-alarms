package ro.chris.schlechta.exception;

import ro.chris.schlechta.model.StockAlarm;

/**
 * Exception when there is no {@link StockAlarm} found
 */
public class NoStockAlarmFoundException extends RuntimeException {

    public NoStockAlarmFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
