package application.havenskin.repositories;

import application.havenskin.models.ResultTests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultTestsRepository extends JpaRepository<ResultTests, String> {
    List<ResultTests> findByUserId(String userId);
}
