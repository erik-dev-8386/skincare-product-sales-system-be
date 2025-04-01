package application.havenskin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;

import java.util.Set;

@Entity
@Table(name = "Questions")
@Data
public class Questions {
    @Id
    @Column(name = "question_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String questionId;

    @Nationalized
    @Column(name = "question_content", length = 1000)
    private String questionContent;

    @Column(name = "max_mark")
    private Double maxMark;

    @NotNull
    @Column(name = "skin_test_id", length = 50)
    private String skinTestId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "skin_test_id", referencedColumnName = "skin_test_id", insertable = false, updatable = false)
    @ToString.Exclude
    private SkinTests skinTest;

    @OneToMany(mappedBy = "question")
    private Set<Answers> answers;

    @NotNull
    @Column(name = "status")
    private Byte status;
}
