package application.havenskin.repository;

import application.havenskin.BusinessObject.Models.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions, String> {
}
