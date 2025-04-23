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
//    @Query("SELECT c FROM Categories c where c.status = 2")
//    List<Categories> findAllByCategoryName();

    List<Categories> findByStatusOrderByCategoryNameAsc(byte status);


//    @Query("SELECT c FROM Categories c WHERE c.categoryName LIKE %:categoryName%")
//    List<Categories> findByCategoryNameContaining(@Param("categoryName") String categoryName);

    List<Categories> findByCategoryNameContaining(String categoryName);


    boolean existsByCategoryName(String categoryName);
}
