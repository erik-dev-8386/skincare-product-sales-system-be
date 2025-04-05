package application.havenskin.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.util.Date;

@Entity
@Table(name = "MiniSkinCarePlan")
@Data
public class MiniSkinCarePlan {
    @Id
    @Column(name = "mini_skin_care_plan_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String miniSkinCarePlanId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "skin_care_plan_id", referencedColumnName = "skin_care_plan_id")
    private SkinCaresPlan skinCarePlan;

    @NotNull
    @Column(name = "step_number")
    private Integer stepNumber;

    @NotNull
    @Column(name = "action", length = 250)
    @Nationalized
    private String Action;

    @Column(name = "status", length = 50)
    private byte status;

}

