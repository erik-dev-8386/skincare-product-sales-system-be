package application.havenskin.repositories;

import application.havenskin.models.Answers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answers, String> {

    @Query("SELECT a FROM Answers a WHERE a.questionId = :questionId")
    List<Answers> findByQuestionId(@Param("questionId") String questionId);

    // Tìm danh sách Answers có answerContent chứa từ khoá
    @Query("SELECT a FROM Answers a WHERE LOWER(a.answerContent) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Answers> searchByContent(@Param("keyword") String keyword);
}
