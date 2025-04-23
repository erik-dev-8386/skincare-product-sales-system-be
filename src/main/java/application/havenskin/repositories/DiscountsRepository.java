package application.havenskin.repositories;

import application.havenskin.models.Discounts;
import application.havenskin.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountsRepository extends JpaRepository<Discounts, String> {
    Discounts findByDiscountId(String discountId);
    Discounts findByDiscountName(String discountName);
//    @Query("SELECT d FROM Discounts d where d.status = 2")
//    List<Discounts> findAllDiscountByName();
    List<Discounts> findByStatusOrderByCreatedTimeAsc(byte status);

//    @Query("SELECT d FROM Discounts d WHERE d.discountName LIKE %:discountsName%")
//    List<Discounts> findByDiscountsNameContaining(@Param("discountsName") String discountsName);

    List<Discounts> findByDiscountNameContaining(String discountName);

    boolean existsByDiscountName(String discountName);
}
