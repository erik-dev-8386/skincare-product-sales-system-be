package application.havenskin.BusinessObject.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
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
    private Double totalMark;

    @NotNull
    @Column(name = "user_id", length = 50)
    private String userId;

    @NotNull
    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "deleted_time")
    private LocalDateTime deletedTime;

    @Column(name = "status")
    private byte status;

    @NotNull
    @Column(name = "skin_type_id", length = 50)
    private String skinTypeId;

    @ManyToOne
    @JoinColumn(name = "skin_type_id", referencedColumnName = "skin_type_id", insertable = false, updatable = false)
    private SkinTypes skinType;

    @NotNull
    @Column(name = "skin_test_id", length = 50)
    private String skinTestId;

    @ManyToOne
    @JoinColumn(name = "skin_test_id", referencedColumnName = "skin_test_id", insertable = false, updatable = false)
    private SkinTests skinTest;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private Users user;

}
