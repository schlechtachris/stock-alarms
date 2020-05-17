package ro.chris.schlechta.model;

import javax.persistence.Column;

public class AlarmDto {

    private String stockSymbol;

    private Double initialPrice;

    private Double currentPrice;

    private Double positiveVariance;

    private Double negativeVariance;

    private boolean active;

    public String getStockSymbol() {
        return stockSymbol;
    }

    public AlarmDto setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
        return this;
    }

    public Double getInitialPrice() {
        return initialPrice;
    }

    public AlarmDto setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
        return this;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public AlarmDto setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
        return this;
    }

    public Double getPositiveVariance() {
        return positiveVariance;
    }

    public AlarmDto setPositiveVariance(Double positiveVariance) {
        this.positiveVariance = positiveVariance;
        return this;
    }

    public Double getNegativeVariance() {
        return negativeVariance;
    }

    public AlarmDto setNegativeVariance(Double negativeVariance) {
        this.negativeVariance = negativeVariance;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public AlarmDto setActive(boolean active) {
        this.active = active;
        return this;
    }
}
