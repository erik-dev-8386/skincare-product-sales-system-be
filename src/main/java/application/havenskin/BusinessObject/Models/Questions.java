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
    private String questionId;

    @Column(name = "question_content", length = 50)
    private String questionContent;

    @Column(name = "max_mark")
    private Double maxMark;

    @NotNull
    @Column(name = "skin_test_id", length = 50)
    private String skinTestId;

    @ManyToOne
    @JoinColumn(name = "skin_test_id", referencedColumnName = "skin_test_id", insertable = false, updatable = false)
    private SkinTests skinTest;

}
