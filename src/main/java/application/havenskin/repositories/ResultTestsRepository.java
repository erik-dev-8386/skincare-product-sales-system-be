package application.havenskin.repositories;

import application.havenskin.models.ResultTests;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultTestsRepository  extends JpaRepository<ResultTests, String> {

    List<ResultTests> findByUserId(String userId);
}
