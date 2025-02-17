package application.havenskin.repositories;

import application.havenskin.models.SkinTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkinTypesRepository extends JpaRepository<SkinTypes, String> {
    SkinTypes findBySkinTypeId(String id);
    SkinTypes findBySkinName(String skinName);
    @Query("SELECT skinName FROM SkinTypes ")
    List<String> findAllBySkinTypeByName();
}
