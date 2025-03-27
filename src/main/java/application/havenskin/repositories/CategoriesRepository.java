package application.havenskin.repositories;

import application.havenskin.models.Categories;
import application.havenskin.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, String> {
    Categories getById(String id);
    Categories findByCategoryName(String name);
    List<Categories> findByStatus(byte status);
    @Query("SELECT categoryName FROM Categories ")
    List<String> findAllByCategoryName();
    @Query("SELECT c FROM Categories c WHERE c.status = 2 ORDER BY c.categoryName ASC")
    List<Categories> findActiveCategorySortedByName();

    @Query("SELECT c FROM Categories c WHERE c.categoryName LIKE %:categoryName%")
    List<Categories> findByCategoryNameContaining(@Param("categoryName") String categoryName);

    boolean existsByCategoryName(String categoryName);
}
