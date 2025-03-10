package application.havenskin.repositories;

import application.havenskin.models.SkinTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkinTypesRepository extends JpaRepository<SkinTypes, String> {
    SkinTypes findBySkinTypeId(String id);
    Optional<SkinTypes> findBySkinName(String skinName);
}
