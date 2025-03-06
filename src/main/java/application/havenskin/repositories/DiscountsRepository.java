package application.havenskin.repositories;

import application.havenskin.models.Discounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountsRepository extends JpaRepository<Discounts, String> {
    Discounts findByDiscountId(String discountId);
    Discounts findByDiscountName(String discountName);
    @Query("SELECT discountName FROM Discounts ")
    List<String> findAllDiscountByName();
    @Query("SELECT d FROM Discounts d WHERE d.status = 2 ORDER BY d.createdTime ASC")
    List<Discounts> findActiveDiscountsSortedByName();
}
