package application.havenskin.repositories;

import application.havenskin.models.OrderDetails;
import application.havenskin.models.Users;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, String> {
    //Optional<OrderDetails> findByOrderId(String id);
    List<OrderDetails> findByOrderId(String id);


    Optional<OrderDetails> findByOrderIdAndProductId(@NotNull String orderId, @NotNull String productId);
//    Optional<OrderDetails> findByOrderIdAndCustomerId(String orderId, String customerId);

}
