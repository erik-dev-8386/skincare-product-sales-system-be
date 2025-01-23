package application.havenskin.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "SkinCarePlans")
@Data
public class SkinCarePlans {
    @Id
    @Column(name = "skin_care_plan_id", length = 50)
    private String skinCarePlanId;

    @NotNull
    @Column(name = "skin_type_id", length = 50)
    private String skinTypeId;

    @Column(name = "description", length = 250)
    private String description;

    @OneToOne
    @JoinColumn(name = "skin_type_id", referencedColumnName = "skin_type_id", insertable = false, updatable = false)
    private SkinTypes skinType;

    @OneToMany(mappedBy = "skinCarePlans")
    private List<PartPlans> partPlans;

}
