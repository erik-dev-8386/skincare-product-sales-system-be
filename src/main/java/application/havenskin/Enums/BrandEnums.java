package application.havenskin.Enums;

public enum BrandEnums {
    ACTIVE((byte) 1),
    Inactive((byte) 2);

    private final byte value;

    BrandEnums(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
