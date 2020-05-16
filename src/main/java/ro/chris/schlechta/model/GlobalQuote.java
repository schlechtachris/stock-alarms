package ro.chris.schlechta.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response object for query Alpha Vantage API Quote
 */
public class GlobalQuote {

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
    private String previosClose;

    @JsonProperty(value = "09. change")
    private String change;

    @JsonProperty(value = "10. change percent")
    private String changePercent;

    public String getSymbol() {
        return symbol;
    }

    public GlobalQuote setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public String getOpen() {
        return open;
    }

    public GlobalQuote setOpen(String open) {
        this.open = open;
        return this;
    }

    public String getHigh() {
        return high;
    }

    public GlobalQuote setHigh(String high) {
        this.high = high;
        return this;
    }

    public String getLow() {
        return low;
    }

    public GlobalQuote setLow(String low) {
        this.low = low;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public GlobalQuote setPrice(String price) {
        this.price = price;
        return this;
    }

    public String getVolume() {
        return volume;
    }

    public GlobalQuote setVolume(String volume) {
        this.volume = volume;
        return this;
    }

    public String getLastTradingDay() {
        return lastTradingDay;
    }

    public GlobalQuote setLastTradingDay(String lastTradingDay) {
        this.lastTradingDay = lastTradingDay;
        return this;
    }

    public String getPreviosClose() {
        return previosClose;
    }

    public GlobalQuote setPreviosClose(String previosClose) {
        this.previosClose = previosClose;
        return this;
    }

    public String getChange() {
        return change;
    }

    public GlobalQuote setChange(String change) {
        this.change = change;
        return this;
    }

    public String getChangePercent() {
        return changePercent;
    }

    public GlobalQuote setChangePercent(String changePercent) {
        this.changePercent = changePercent;
        return this;
    }
}
