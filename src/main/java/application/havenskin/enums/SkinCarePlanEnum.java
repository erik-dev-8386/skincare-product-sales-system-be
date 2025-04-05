package application.havenskin.enums;

public enum SkinCarePlanEnum {
    INACTIVE((byte) 0),
    ACTIVE((byte) 1);

    private final byte skinCarePlan_status;

    SkinCarePlanEnum(byte skinCarePlan_status) {
        this.skinCarePlan_status = skinCarePlan_status;
    }

    public byte getSkinCarePlan_status() {
        return skinCarePlan_status;
    }
    public SkinCarePlanEnum getSkinCarePlanEnum(byte status) {
        for (SkinCarePlanEnum s : SkinCarePlanEnum.values()) {
            if (s.getSkinCarePlan_status() == status) {
                return s;
            }
        }
        throw new IllegalArgumentException("No SkinCarePlanEnum found with status " + status);
    }
}
