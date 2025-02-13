package application.havenskin.repositories;

import application.havenskin.models.SkinTypeImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkinTypeImagesRepository extends JpaRepository<SkinTypeImages, String> {
}
