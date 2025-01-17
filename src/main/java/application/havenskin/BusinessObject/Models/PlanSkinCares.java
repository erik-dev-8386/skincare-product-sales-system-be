package application.havenskin.BusinessObject.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "PlanSkinCares")
@Data
public class PlanSkinCares {
    @Id
    @Column(name = "plan_skin_care_id", length = 50)
    private String planSkinCareId;

    @NotNull
    @Column(name = "skin_type_id", length = 50)
    private String skinTypeId;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "result_test_id", length = 50)
    private String resultTestId;

    @OneToOne
    @JoinColumn(name = "skin_type_id", referencedColumnName = "skin_type_id", insertable = false, updatable = false)
    private SkinTypes skinType;

}
