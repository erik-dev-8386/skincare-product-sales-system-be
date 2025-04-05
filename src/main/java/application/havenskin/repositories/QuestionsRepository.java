package application.havenskin.repositories;

import application.havenskin.models.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions, String> {
    @Query("SELECT q FROM Questions q WHERE LOWER(q.questionContent) LIKE LOWER(CONCAT('%', :content, '%'))")
    List<Questions> searchByContent(@Param("content") String content);

    @Query("SELECT q  FROM Questions q WHERE q.skinTestId = :skinTestId and q.status = 1")
    List<Questions> findBySkinTestId(String skinTestId);

    List<Questions> findByQuestionContent(String questionContent);

    @Query("SELECT q.questionContent FROM Questions q")
    List<String> findAllQuestionContents();

}
