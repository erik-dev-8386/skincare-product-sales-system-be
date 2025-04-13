package application.havenskin.enums;

public enum SkinTypeEnums {
    ACTIVE ((byte) 1),
    INACTIVE ((byte) 2);
    private final byte skin_type_status;

    SkinTypeEnums(byte skin_type_status) {
        this.skin_type_status = skin_type_status;
    }

    public byte getSkin_type_status() {
        return skin_type_status;
    }

    public static SkinTypeEnums getSkinTypeStatus (byte skin_type_status) {
        for (SkinTypeEnums x : SkinTypeEnums.values()) {
            if (x.skin_type_status == skin_type_status) {
                return x;
            }
        }
        throw new IllegalArgumentException("Invalid skin type status: " + skin_type_status);
    }
}
