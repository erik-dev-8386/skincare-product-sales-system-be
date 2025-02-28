package application.havenskin.repositories;

import application.havenskin.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, String> {
    List<OrderDetails> findByOrderId(String id);

    @Query("SELECT od FROM OrderDetails od " +
            "JOIN od.orders o " +
            "WHERE o.userId = :userId AND od.productId = :productId")
    Optional<OrderDetails> findByUserIdAndProductId(@Param("userId") String userId, @Param("productId") String productId);

}
