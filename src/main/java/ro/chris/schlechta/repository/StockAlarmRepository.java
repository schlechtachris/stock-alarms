package ro.chris.schlechta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.chris.schlechta.model.StockAlarm;
import ro.chris.schlechta.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockAlarmRepository extends JpaRepository<StockAlarm, Long> {

    Optional<StockAlarm> findByStockSymbolAndUser(String stockSymbol, User user);

    Optional<StockAlarm> findByStockSymbol(String stockSymbol);

    List<StockAlarm> findAllByUser(User user);

    void deleteAllByUser(User user);

}
