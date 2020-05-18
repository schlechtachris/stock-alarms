package ro.chris.schlechta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.chris.schlechta.model.User;
import ro.chris.schlechta.model.UserStock;

import java.util.List;

@Repository
public interface UserStockRepository extends JpaRepository<UserStock, Long> {

    List<UserStock> findAllByUser(User user);

}
