package application.havenskin.repositories;

import application.havenskin.models.SkinTests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkinTestsRepository extends JpaRepository<SkinTests, String> {
}
