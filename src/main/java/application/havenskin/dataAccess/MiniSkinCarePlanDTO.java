package application.havenskin.dataAccess;

import application.havenskin.enums.MiniSkinCarePlanEnum;
import application.havenskin.models.MiniSkinCarePlan;
import application.havenskin.models.SkinCaresPlan;
import application.havenskin.models.SkinTypes;


import java.util.List;

public class MiniSkinCarePlanDTO {

    private SkinCaresPlan skinCarePlan;

    private String description;

    private SkinTypes skinType;

    private List<MiniSkinCarePlan> miniSkinCarePlans;

    private Byte status = MiniSkinCarePlanEnum.ACTIVE.getMini_skin_care_plan_status();
}
