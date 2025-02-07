package application.havenskin.Repositories;

import application.havenskin.Models.SkinCarePlans;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanSkinCareRepository extends JpaRepository<SkinCarePlans, String> {
}
