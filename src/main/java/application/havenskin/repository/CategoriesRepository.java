package application.havenskin.repository;

import application.havenskin.BusinessObject.Models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, String> {
//    Categories getById(String id);
    Categories findByCategoryName(String name);
}
