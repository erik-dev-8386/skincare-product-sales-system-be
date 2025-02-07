package application.havenskin.repository;

import application.havenskin.BusinessObject.Models.Discounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountsRepository extends JpaRepository<Discounts, String> {
    Discounts findByDiscountId(String discountId);

    Discounts findByDiscountName(String discountName);
}
