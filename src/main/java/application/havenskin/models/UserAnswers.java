package application.havenskin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Table(name = "user_answers")
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswers {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userAnswersId;

    // Kết quả test mà đáp án này thuộc về
    @ManyToOne
    @JoinColumn(name = "result_test_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private ResultTests resultTest;

    // Câu hỏi mà user trả lời
    @NotNull
    @Column(name = "question_id", length = 50)
    private String questionId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "question_id", nullable = false, insertable=false, updatable=false)
    private Questions question;

    @NotNull
    @Column(name = "answer_id", length = 50)
    private String answerId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "answer_id", nullable = false, insertable=false, updatable=false)
    private Answers answer;

    private Double mark;
}
