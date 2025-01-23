package application.havenskin.BusinessObject.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "SkinCaresPlan")
@Data
public class SkinCaresPlan {
    @Id
    @Column(name = "skin_care_plan_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String SkinCarePlanId;

    @NotNull
    @Column(name = "skin_type_id", length = 50)
    private String skinTypeId;

    @Column(name = "description", length = 250)
    private String description;

    @OneToOne
    @JoinColumn(name = "skin_type_id", referencedColumnName = "skin_type_id", insertable = false, updatable = false)
    private SkinTypes skinType;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user;

    @OneToMany(mappedBy = "skinCarePlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MiniSkinCarePlan> miniSkinCarePlans;

}
