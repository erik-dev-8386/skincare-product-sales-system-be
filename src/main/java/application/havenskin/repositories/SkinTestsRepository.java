package application.havenskin.repositories;

import application.havenskin.models.SkinTests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SkinTestsRepository extends JpaRepository<SkinTests, String> {

}
