package application.havenskin.repositories;

import application.havenskin.models.MiniSkinCarePlan;
import application.havenskin.models.SkinCaresPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MiniSkinCareRepository extends JpaRepository<MiniSkinCarePlan, String> {
    @Query("SELECT m FROM MiniSkinCarePlan m where m.action = :action and m.skinCarePlan.SkinCarePlanId = :skinCarePlanId")
    MiniSkinCarePlan findByActionAndSkinCarePlanId(String action, String skinCarePlanId);

//    @Query("SELECT m FROM MiniSkinCarePlan  m where m.action like %:action%")
//    List<MiniSkinCarePlan> findAllMiniSkinCarePlanByAction(String action);

    List<MiniSkinCarePlan> findByActionContaining(String action);


//    @Query("SELECT m FROM MiniSkinCarePlan m where m.status = 1")
//    List<MiniSkinCarePlan> findAllMiniSkinCarePlanByStatus();

    List<MiniSkinCarePlan> findByStatus(byte status);


    @Query("SELECT m FROM MiniSkinCarePlan m where m.stepNumber = :stepNumber and m.skinCarePlan.SkinCarePlanId = :skinCarePlanId and m.status = 1")
    List<MiniSkinCarePlan> findByStepNumberAndSkinCarePlanIdAndStatus(
            int stepNumber,
            String skinCarePlanId,
            byte status
    );


}
