package ro.chris.schlechta.model.persisted;

import javax.persistence.*;

@Entity
@Table(name = "stock_alarm", schema = "stock_alarms")
public class StockAlarm {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "stock_symbol", nullable = false)
    private String stockSymbol;

    @Column(name = "initial_price", nullable = false)
    private Double initialPrice;

    @Column(name = "current_price", nullable = false)
    private Double currentPrice;

    @Column(name = "positive_variance", nullable = false)
    private Double positiveVariance;

    @Column(name = "negative_variance", nullable = false)
    private Double negativeVariance;

    @Column(name = "active", nullable = false)
    private boolean active;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Long getId() {
        return id;
    }

    public StockAlarm setId(Long id) {
        this.id = id;
        return this;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public StockAlarm setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
        return this;
    }

    public Double getInitialPrice() {
        return initialPrice;
    }

    public StockAlarm setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
        return this;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public StockAlarm setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
        return this;
    }

    public Double getPositiveVariance() {
        return positiveVariance;
    }

    public StockAlarm setPositiveVariance(Double positiveVariance) {
        this.positiveVariance = positiveVariance;
        return this;
    }

    public Double getNegativeVariance() {
        return negativeVariance;
    }

    public StockAlarm setNegativeVariance(Double negativeVariance) {
        this.negativeVariance = negativeVariance;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public StockAlarm setActive(boolean active) {
        this.active = active;
        return this;
    }

    public User getUser() {
        return user;
    }

    public StockAlarm setUser(User user) {
        this.user = user;
        return this;
    }
}
