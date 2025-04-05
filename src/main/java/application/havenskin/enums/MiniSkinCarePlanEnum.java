package application.havenskin.enums;

public enum MiniSkinCarePlanEnum {
    INACTIVE((byte) 0),
    ACTIVE((byte) 1);
    private byte mini_skin_care_plan_status;

    MiniSkinCarePlanEnum(byte mini_skin_care_plan_status) {
        this.mini_skin_care_plan_status = mini_skin_care_plan_status;
    }

    public byte getMini_skin_care_plan_status() {
        return mini_skin_care_plan_status;
    }
    public MiniSkinCarePlanEnum getMiniSkinCarePlanEnum(byte status) {
        for (MiniSkinCarePlanEnum e : MiniSkinCarePlanEnum.values()) {
            if (e.mini_skin_care_plan_status == status) {
                return e;
            }
        }
        throw new RuntimeException("No MiniSkinCarePlanEnum found for " + status);
    }
}
