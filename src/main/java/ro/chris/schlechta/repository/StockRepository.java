package ro.chris.schlechta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.chris.schlechta.model.persisted.Stock;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByStockSymbol(String symbol);

}
