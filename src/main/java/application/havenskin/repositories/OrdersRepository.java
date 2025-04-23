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

    List<Orders> findByOrderId(String orderID);

    Optional<Orders> findByUserIdAndStatus(@NotNull String userId, @NotNull byte status);

    Optional<Orders> findByOrderIdAndUserId(@NotNull String orderId, @NotNull String userId);

//    @Query("SELECT o FROM Orders o where o.orderId LIKE %:orderId%")
//    List<Orders> findByOrderIdContaining(@Param("orderId") String orderId);


    List<Orders> findByOrderIdContaining(String orderId);

    List<Orders> findByStatus(byte status);


    List<Orders> findAllByOrderByOrderTimeDesc();

    List<Orders> findAllByOrderByOrderTimeAsc();

//    @Query("SELECT o FROM Orders o where o.userId = :userId and o.status <> 0 ORDER BY o.orderTime DESC ")
//    List<Orders> sortOrdersByUserIdAndOrderTimeDesc(@Param("userId") String userId);

    List<Orders> findByUserIdAndStatusNotOrderByOrderTimeDesc(String userId, byte status);


//    List<Orders> findByUserIdAndStatusNotOrderByOrderTimeDesc(String userId, byte status);

    List<Orders> findByUserIdAndStatusNotOrderByOrderTimeAsc(String userId, byte status);


    @Query("SELECT o FROM Orders o WHERE o.userId = :userId AND o.orderId NOT IN" + "(SELECT o1.orderId FROM Orders o1 where o1.status = :status)")
    List<Orders> findListOrderByStatus(String userId, byte status);

//    @Query("SELECT o FROM Orders o where o.status <> 0")
//    List<Orders> findByAll();

    List<Orders> findByStatusNot(byte status);

}
