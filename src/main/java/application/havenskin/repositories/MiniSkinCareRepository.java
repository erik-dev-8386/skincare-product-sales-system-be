package application.havenskin.repositories;

import application.havenskin.models.MiniSkinCarePlan;
import application.havenskin.models.SkinCaresPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MiniSkinCareRepository extends JpaRepository<MiniSkinCarePlan, String> {
    MiniSkinCarePlan findByAction(String action);
    @Query("SELECT m FROM MiniSkinCarePlan  m where m.action like %:action%")
    List<MiniSkinCarePlan> findAllMiniSkinCarePlanByAction(String aciton);
    @Query("SELECT m FROM MiniSkinCarePlan m where m.status = 1")
    List<MiniSkinCarePlan> findAllMiniSkinCarePlanByStatus();
}
