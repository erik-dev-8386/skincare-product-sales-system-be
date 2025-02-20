package application.havenskin.repositories;

import application.havenskin.models.UserAnswers;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAnswersRepository extends JpaRepository<UserAnswers, String> {
    List<UserAnswers> findByResultTest_ResultTestId(String resultTestId);
}
