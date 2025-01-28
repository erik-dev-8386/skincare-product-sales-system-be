package application.havenskin.BusinessObject.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Questions")
@Data
public class Questions {
    @Id
    @Column(name = "question_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String questionId;

    @Column(name = "question_content", length = 50)
    private String questionContent;

    @Column(name = "max_mark")
    private Double maxMark;
}
