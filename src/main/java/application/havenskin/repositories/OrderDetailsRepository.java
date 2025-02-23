package application.havenskin.repositories;

import application.havenskin.models.OrderDetails;
import application.havenskin.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, String> {
    //Optional<OrderDetails> findByOrderId(String id);
    List<OrderDetails> findByOrderId(String id);
}
