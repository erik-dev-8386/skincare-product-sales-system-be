package application.havenskin.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "Questions")
@Data
public class Questions {
    @Id
    @Column(name = "question_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String questionId;

    @Column(name = "question_content", length = 50)
    @Nationalized
    private String questionContent;

    @Column(name = "max_mark")
    private Double maxMark;
}
