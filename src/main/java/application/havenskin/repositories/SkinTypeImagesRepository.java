package application.havenskin.repositories;

import application.havenskin.models.SkinTypeImages;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkinTypeImagesRepository extends JpaRepository<SkinTypeImages, String> {
    List<SkinTypeImages> findBySkinTypeId(@NotNull String skinTypeId);
}
