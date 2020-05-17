package ro.chris.schlechta.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response object for query Alpha Vantage API Quote
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalQuoteItem {

    @JsonProperty(value = "01. symbol")
    private String symbol;

    @JsonProperty(value = "02. open")
    private String open;

    @JsonProperty(value = "03. high")
    private String high;

    @JsonProperty(value = "04. low")
    private String low;

    @JsonProperty(value = "05. price")
    private String price;

    @JsonProperty(value = "06. volume")
    private String volume;

    @JsonProperty(value = "07. latest trading day")
    private String lastTradingDay;

    @JsonProperty(value = "08. previous close")
    private String previousClose;

    @JsonProperty(value = "09. change")
    private String change;

    @JsonProperty(value = "10. change percent")
    private String changePercent;

    public String getSymbol() {
        return symbol;
    }

    public GlobalQuoteItem setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public String getOpen() {
        return open;
    }

    public GlobalQuoteItem setOpen(String open) {
        this.open = open;
        return this;
    }

    public String getHigh() {
        return high;
    }

    public GlobalQuoteItem setHigh(String high) {
        this.high = high;
        return this;
    }

    public String getLow() {
        return low;
    }

    public GlobalQuoteItem setLow(String low) {
        this.low = low;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public GlobalQuoteItem setPrice(String price) {
        this.price = price;
        return this;
    }

    public String getVolume() {
        return volume;
    }

    public GlobalQuoteItem setVolume(String volume) {
        this.volume = volume;
        return this;
    }

    public String getLastTradingDay() {
        return lastTradingDay;
    }

    public GlobalQuoteItem setLastTradingDay(String lastTradingDay) {
        this.lastTradingDay = lastTradingDay;
        return this;
    }

    public String getPreviousClose() {
        return previousClose;
    }

    public GlobalQuoteItem setPreviousClose(String previousClose) {
        this.previousClose = previousClose;
        return this;
    }

    public String getChange() {
        return change;
    }

    public GlobalQuoteItem setChange(String change) {
        this.change = change;
        return this;
    }

    public String getChangePercent() {
        return changePercent;
    }

    public GlobalQuoteItem setChangePercent(String changePercent) {
        this.changePercent = changePercent;
        return this;
    }
}
