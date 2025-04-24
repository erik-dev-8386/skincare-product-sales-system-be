package application.havenskin.specification;

import application.havenskin.models.Products;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpec {

    public static Specification<Products> searchProductName(String productName) {
        return (root, query, criteriaBuilder) -> {
            if (productName == null || productName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Predicate namePredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("productName")),
                    "%" + productName.toLowerCase() + "%");
            return namePredicate;
        };
        }
    public static Specification<Products> findByStatusProduct() {
        return (root, query, criteriaBuilder) -> {
            // Lọc sản phẩm có status = 1
            return criteriaBuilder.equal(root.get("status"), 1);
        };
    }
    }

