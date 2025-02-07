package application.havenskin.Repositories;

import application.havenskin.Models.SkinTypes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkinTypeRepository extends JpaRepository<SkinTypes, String> {
}
