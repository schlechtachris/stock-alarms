package ro.chris.schlechta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.chris.schlechta.model.persisted.StockSymbol;

@Repository
public interface StockSymbolRepository extends JpaRepository<StockSymbol, Long> {
}
