package application.havenskin.repositories;

import application.havenskin.models.SkinCaresPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanSkinCareRepository extends JpaRepository<SkinCaresPlan, String> {
}
