package application.havenskin.dataAccess;

import application.havenskin.enums.SkinCarePlanEnum;
import application.havenskin.enums.SkinTypeEnums;
import application.havenskin.models.MiniSkinCarePlan;
import application.havenskin.models.SkinTypes;
import application.havenskin.models.Users;
import lombok.Data;

import java.util.List;
@Data
public class PlanSkinCareDTO {
    private String skinTypeId;

    private String description;

    private SkinTypes skinType;

    private List<MiniSkinCarePlan> miniSkinCarePlans;

    private Byte status = SkinCarePlanEnum.ACTIVE.getSkinCarePlan_status();
}
