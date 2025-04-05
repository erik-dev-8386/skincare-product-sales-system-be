package application.havenskin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "Answers")
@Data
public class Answers {
    @Id
    @Column(name = "answer_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String answerId;

    @Column(name = "answer_content", length = 1000)
    @Nationalized
    private String answerContent;

    @Column(name = "mark")
    private Double mark;

    @NotNull
    @Column(name = "question_id", length = 50)
    private String questionId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "question_id", referencedColumnName = "question_id", insertable = false, updatable = false)
    private Questions question;


//    @NotNull
    @Column(name = "status")
    private Byte status;
}
