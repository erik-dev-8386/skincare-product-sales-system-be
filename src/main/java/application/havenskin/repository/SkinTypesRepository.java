package application.havenskin.repository;

import application.havenskin.BusinessObject.Models.SkinTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkinTypesRepository extends JpaRepository<SkinTypes, String> {
    SkinTypes findBySkinTypeId(String id);
    SkinTypes findBySkinName(String skinName);
}
