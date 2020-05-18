package ro.chris.schlechta.dto;

public class StockAlarmDto {

    private String stockSymbol;

    private Double initialPrice;

    private Double currentPrice;

    private Double positiveVariance;

    private Double negativeVariance;

    private boolean active;

    public String getStockSymbol() {
        return stockSymbol;
    }

    public StockAlarmDto setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
        return this;
    }

    public Double getInitialPrice() {
        return initialPrice;
    }

    public StockAlarmDto setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
        return this;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public StockAlarmDto setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
        return this;
    }

    public Double getPositiveVariance() {
        return positiveVariance;
    }

    public StockAlarmDto setPositiveVariance(Double positiveVariance) {
        this.positiveVariance = positiveVariance;
        return this;
    }

    public Double getNegativeVariance() {
        return negativeVariance;
    }

    public StockAlarmDto setNegativeVariance(Double negativeVariance) {
        this.negativeVariance = negativeVariance;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public StockAlarmDto setActive(boolean active) {
        this.active = active;
        return this;
    }
}
