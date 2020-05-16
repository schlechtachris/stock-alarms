package ro.chris.schlechta.model;

import javax.persistence.*;

@Entity
@Table(name = "stock", schema = "stock_alarms")
public class Stock {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "stock_symbol")
    private String stockSymbol;

    @Column(name = "price")
    private double price;

    public Long getId() {
        return id;
    }

    public Stock setId(Long id) {
        this.id = id;
        return this;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public Stock setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Stock setPrice(double price) {
        this.price = price;
        return this;
    }
}
