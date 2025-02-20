package application.havenskin.repositories;

import application.havenskin.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, String> {
    Categories getById(String id);
    Categories findByCategoryName(String name);
    List<Categories> findByStatus(byte status);
    @Query("SELECT categoryName FROM Categories ")
    List<String> findAllByCategoryName();
}
