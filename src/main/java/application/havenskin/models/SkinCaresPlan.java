package application.havenskin.models;

import application.havenskin.enums.SkinCarePlanEnum;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;

import java.util.List;

@Entity
@Table(name = "SkinCaresPlan")
@Data
public class SkinCaresPlan {
    @Id
    @Column(name = "skin_care_plan_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String SkinCarePlanId;
//
//    @NotNull
//    @Column(name = "skin_type_id", length = 50)
//    private String skinTypeId;

    @Column(name = "description", length = 10000)
    @Nationalized
    private String description;

    @ToString.Exclude
//    @JsonIgnore
//    @JsonManagedReference
    //@JsonBackReference
//    @JsonIgnoreProperties("planSkinCare")
    @ManyToOne
    @JoinColumn(name = "skin_type_id", referencedColumnName = "skin_type_id")
    private SkinTypes skinType;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "skinCarePlan", cascade = CascadeType.ALL)
    private List<MiniSkinCarePlan> miniSkinCarePlans;

    @NotNull
    @Column(name = "status")
    private byte status = SkinCarePlanEnum.ACTIVE.getSkinCarePlan_status();

}
