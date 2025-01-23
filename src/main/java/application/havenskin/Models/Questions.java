package application.havenskin.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "Questions")
@Data
public class Questions {
    @Id
    @Column(name = "question_id", length = 50)
    private String questionId;

    @Column(name = "question_content", length = 50)
    private String questionContent;

    @Column(name = "max_mark")
    private Double maxMark;


}
