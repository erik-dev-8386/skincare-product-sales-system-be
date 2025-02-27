package application.havenskin.repositories;

import application.havenskin.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, String> {
    List<OrderDetails> findByOrderId(String id);
//    Optional<OrderDetails> findByOrderIdAndCustomerId(String orderId, String customerId);

}
