package application.havenskin.repositories;

import application.havenskin.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products, String> {
    List<Products> findByCategoryId(String id);
    List<Products> findByBrandId(String id);
    List<Products> findByQuantity(Integer quantiy);
}
