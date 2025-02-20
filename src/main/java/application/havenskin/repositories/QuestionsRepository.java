package application.havenskin.repositories;

import application.havenskin.models.Questions;
import application.havenskin.models.SkinTests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions, String> {
    @Query("SELECT q FROM Questions q WHERE LOWER(q.questionContent) LIKE LOWER(CONCAT('%', :content, '%'))")
    List<Questions> searchByContent(@Param("content") String content);

    List<Questions> findBySkinTestId(String skinTestId);
}
