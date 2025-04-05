package application.havenskin.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
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

//    @NotNull
//    @Column(name = "skin_type_id", length = 50)
//    private String skinTypeId;

    @Column(name = "description", length = 10000)
    @Nationalized
    private String description;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "skin_type_id", referencedColumnName = "skin_type_id")
    private SkinTypes skinType;


    @OneToMany(mappedBy = "skinCarePlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MiniSkinCarePlan> miniSkinCarePlans;

    @NotNull
    @Column(name = "status")
    private Byte status;

}
