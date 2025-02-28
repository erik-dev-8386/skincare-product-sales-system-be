package application.havenskin.repositories;

import application.havenskin.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, String> {
    @Query("SELECT o FROM Orders o WHERE o.userId = :userId AND o.status = :status")
    Optional<Orders> findByUserIdAndStatus(@Param("userId")String userId, @Param("status")byte status);
}
