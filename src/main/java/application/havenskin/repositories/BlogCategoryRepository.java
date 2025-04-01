package application.havenskin.repositories;

import application.havenskin.models.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogCategoryRepository extends JpaRepository<BlogCategory, String> {

    Optional<BlogCategory> findByBlogCategoryName(String blogCategoryName);
}
