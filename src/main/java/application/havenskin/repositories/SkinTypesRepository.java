package application.havenskin.repositories;

import application.havenskin.models.Products;
import application.havenskin.models.SkinTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkinTypesRepository extends JpaRepository<SkinTypes, String> {
    SkinTypes findBySkinTypeId(String id);
    Optional<SkinTypes> findBySkinName(String skinName);
    @Query("SELECT s FROM SkinTypes s where s.status = 1 ")
    List<SkinTypes> findAllBySkinTypeByName();
    @Query("SELECT s FROM SkinTypes s WHERE s.status = 1 ORDER BY s.skinName ASC")
    List<SkinTypes> findActiveSkinTypesSortedByName();


    @Query("SELECT s FROM SkinTypes  s WHERE s.skinName LIKE %:skinName%")
    List<SkinTypes> findBySkinNameContaining(@Param("skinName") String skinName);

    boolean existsBySkinName(String skinName);
}
