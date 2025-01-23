package application.havenskin.repository;

import application.havenskin.BusinessObject.Models.BlogImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogImagesRepository extends JpaRepository<BlogImages, String> {
}
