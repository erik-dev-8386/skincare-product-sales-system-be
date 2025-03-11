package application.havenskin.repositories;

import application.havenskin.models.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogCategoryRepository extends JpaRepository<BlogCategory, String> {

    BlogCategory findByBlogCategoryName(String blogCategoryName);
}
