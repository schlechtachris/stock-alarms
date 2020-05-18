package ro.chris.schlechta.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "stock_symbol", schema = "stock_alarms")
public class StockSymbol {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "company_name")
    private String companyName;

    public Long getId() {
        return id;
    }

    public StockSymbol setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public StockSymbol setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public StockSymbol setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        StockSymbol that = (StockSymbol) o;

        return symbol.equals(that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, symbol, companyName);
    }
}
