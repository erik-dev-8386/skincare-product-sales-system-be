package application.havenskin.repositories;

import application.havenskin.models.MiniSkinCarePlan;
import application.havenskin.models.SkinCaresPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanSkinCareRepository extends JpaRepository<SkinCaresPlan, String> {
    SkinCaresPlan findByDescription(String description);

    @Query("SELECT m FROM SkinCaresPlan m where m.description like %:description%")
    List<SkinCaresPlan> findAllSkinCarePlanByDescription(String description);

    @Query("SELECT m FROM SkinCaresPlan m where m.status = 1")
    List<SkinCaresPlan> findAllSkinCarePlanByStatus();

//    SkinCaresPlan findByDescriptionAndSkinType_Id(String description, String status);

    SkinCaresPlan findByDescriptionAndSkinType_SkinTypeId(String description, String skinTypeId);

    boolean existsByDescriptionAndSkinType_SkinTypeId(String description, String skinTypeId);
}
