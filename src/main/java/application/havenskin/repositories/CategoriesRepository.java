package application.havenskin.repositories;

import application.havenskin.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, String> {
    Categories getById(String id);
    Categories findBycategoryName(String name);

    Categories findByCategoryName(String categoryName);
}
