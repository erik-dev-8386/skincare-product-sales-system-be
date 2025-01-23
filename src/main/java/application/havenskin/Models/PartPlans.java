package application.havenskin.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "PartPlans")
@Data
public class PartPlans {
    @Id
    @Column(name = "part_plan_id", length = 50)
    private String partPlanId;

    @Column(name = "description", length = 250)
    private String description;

    @NotNull
    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @NotNull
    @Column(name = "user_id", length = 50)
    private String userId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private Users users;

    @NotNull
    @Column(name = "skin_care_plan_id", length = 50)
    private String skinCarePlanId;

    @ManyToOne
    @JoinColumn(name = "skin_care_plan_id", referencedColumnName = "skin_care_plan_id", insertable = false, updatable = false)
    private SkinCarePlans skinCarePlans;
}
