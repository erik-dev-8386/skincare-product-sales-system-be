package application.havenskin.repositories;

import application.havenskin.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductsRepository extends JpaRepository<Products, String> {
    List<Products> findByCategoryId(String id);
    List<Products> findByBrandId(String id);
    List<Products> findByQuantity(Integer quantiy);
}
