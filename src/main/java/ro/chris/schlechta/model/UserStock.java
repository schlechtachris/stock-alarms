package ro.chris.schlechta.model;

import javax.persistence.*;

@Entity
@Table(name = "user_stock", schema = "stock_alarms")
public class UserStock {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Long getId() {
        return id;
    }

    public UserStock setId(Long id) {
        this.id = id;
        return this;
    }

    public Stock getStock() {
        return stock;
    }

    public UserStock setStock(Stock stock) {
        this.stock = stock;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserStock setUser(User user) {
        this.user = user;
        return this;
    }
}
