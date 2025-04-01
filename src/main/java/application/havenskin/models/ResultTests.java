package application.havenskin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ResultTests")
@Data
public class ResultTests {
    @Id
    @Column(name = "result_test_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String resultTestId;

    @Column(name = "total_mark")
    private double totalMark;

    @NotNull
    @Column(name = "user_id", length = 50)
    private String userId;

    @NotNull
    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "deleted_time")
    private Date deletedTime;

    @Column(name = "status")
    private byte status;

//    @NotNull
    @Column(name = "skin_type_id", length = 50)
    private String skinTypeId;

    @ManyToOne
    @JoinColumn(name = "skin_type_id", referencedColumnName = "skin_type_id", insertable = false, updatable = false)
    @ToString.Exclude //ngăn vòng lặp khi gọi toString()
    private SkinTypes skinType;

    @NotNull
    @Column(name = "skin_test_id", length = 50)
    private String skinTestId;

    @ManyToOne
    @JoinColumn(name = "skin_test_id", referencedColumnName = "skin_test_id", insertable = false, updatable = false)
    @ToString.Exclude
    private SkinTests skinTest;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    @ToString.Exclude
    private Users user;

    @OneToMany(mappedBy = "resultTest", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<UserAnswers> userAnswers;


}
