package application.havenskin.repositories;

import application.havenskin.models.SkinTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkinTypesRepository extends JpaRepository<SkinTypes, String> {
    SkinTypes findBySkinTypeId(String id);
}
