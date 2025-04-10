package application.havenskin.dataAccess;

import application.havenskin.enums.MiniSkinCarePlanEnum;
import application.havenskin.models.MiniSkinCarePlan;
import application.havenskin.models.SkinCaresPlan;
import application.havenskin.models.SkinTypes;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.Nationalized;


import java.util.List;
@Data
public class MiniSkinCarePlanDTO {
//    private SkinCaresPlan skinCarePlan;

    private int stepNumber;

    private String action;

    private byte status = MiniSkinCarePlanEnum.ACTIVE.getMini_skin_care_plan_status();
}
