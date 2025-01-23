package application.havenskin.repository;

import application.havenskin.BusinessObject.Models.Answers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answers, String> {
}
