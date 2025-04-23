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

//    Optional<OrderDetails> findByOrderIdAndCustomerId(String orderId, String customerId);

//    @Query("SELECT p.productName AS productName, SUM(od.quantity) AS totalQuantitySold " +
//            "FROM OrderDetails od " +
//            "JOIN od.products p " +
//            "GROUP BY p.productName " +
//            "ORDER BY SUM(od.quantity) ASC")
//    List<Object[]> findProductSalesQuantity();

    @Query("SELECT " +
            "YEAR(od.orders.orderTime) as year, " +
            "MONTH(od.orders.orderTime) as month, " +
            "p.productName as productName, " +
            "SUM(od.quantity) as totalQuantitySold " +
            "FROM OrderDetails od " +
            "JOIN od.products p " +
            "GROUP BY YEAR(od.orders.orderTime), MONTH(od.orders.orderTime), p.productName " +
            "ORDER BY YEAR(od.orders.orderTime), MONTH(od.orders.orderTime)")
    List<Object[]> findMonthlyProductSales();
}
