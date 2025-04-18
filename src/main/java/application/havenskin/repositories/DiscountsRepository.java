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
    @Query("SELECT d FROM Discounts d where d.status = 2")
    List<Discounts> findAllDiscountByName();
    @Query("SELECT d FROM Discounts d WHERE d.status = 2 ORDER BY d.createdTime ASC")
    List<Discounts> findActiveDiscountsSortedByName();


    @Query("SELECT d FROM Discounts d WHERE d.discountName LIKE %:discountsName%")
    List<Discounts> findByDiscountsNameContaining(@Param("discountsName") String discountsName);

    boolean existsByDiscountName(String discountName);
}
