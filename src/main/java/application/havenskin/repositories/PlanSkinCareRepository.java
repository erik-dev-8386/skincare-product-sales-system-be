package application.havenskin.repositories;

import application.havenskin.models.MiniSkinCarePlan;
import application.havenskin.models.SkinCaresPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanSkinCareRepository extends JpaRepository<SkinCaresPlan, String> {

    SkinCaresPlan findByDescriptionAndStatus(String description, byte status);


    List<SkinCaresPlan> findByDescriptionContaining(String description);



    List<SkinCaresPlan> findByStatus(byte status);

//    SkinCaresPlan findByDescriptionAndSkinType_Id(String description, String status);

    @Query("SELECT m FROM SkinCaresPlan m where m.description = :description and m.skinType.skinTypeId = :skinTypeId")
    SkinCaresPlan findByDescriptionAndSkinType_SkinTypeId(String description, String skinTypeId);

    boolean existsByDescriptionAndSkinType_SkinTypeId(String description, String skinTypeId);
}
