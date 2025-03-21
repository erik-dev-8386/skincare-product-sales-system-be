package application.havenskin.repositories;

import application.havenskin.models.OrderDetails;
import application.havenskin.models.Orders;
import jakarta.validation.constraints.NotNull;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, String> {

    Optional<Orders> findByUserIdAndStatus(@NotNull String userId, @NotNull byte status);

    Optional<Orders> findByOrderIdAndUserId(@NotNull String orderId, @NotNull String userId);

    List<Orders> findByUserId(String userId);

    @Query("SELECT o FROM Orders o where o.orderId LIKE %:orderId%")
    List<Orders> findByOrderIdContaining(@Param("orderId") String orderId);

    List<Orders> findByStatus(byte status);



    List<Orders> findAllByOrderByOrderTimeDesc();

    List<Orders> findAllByOrderByOrderTimeAsc();

    @Query("SELECT o FROM Orders o where o.userId = :userId ORDER BY o.orderTime DESC")
    List<Orders> sortOrdersByUserIdAndOrderTimeDesc(@Param("userId") String userId);

    @Query("SELECT o FROM Orders o where o.userId = :userId ORDER BY o.orderTime ASC")
    List<Orders> sortOrdersByUserIdAndOrderTimeAsc(@Param("userId") String userId);
}
