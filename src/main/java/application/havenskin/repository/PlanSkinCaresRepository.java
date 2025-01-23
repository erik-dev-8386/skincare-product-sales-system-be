package application.havenskin.repository;

import application.havenskin.BusinessObject.Models.SkinCaresPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanSkinCaresRepository extends JpaRepository<SkinCaresPlan, String>{
}
