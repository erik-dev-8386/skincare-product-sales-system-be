package application.havenskin.Enums;

public enum SkinTypeEnums {
    ;
    private final byte skin_type_status;

    SkinTypeEnums(byte skin_type_status) {
        this.skin_type_status = skin_type_status;
    }

    public byte getSkin_type_status() {
        return skin_type_status;
    }

}
