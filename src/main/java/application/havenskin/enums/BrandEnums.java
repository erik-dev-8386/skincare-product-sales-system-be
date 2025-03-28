package application.havenskin.enums;

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

    public static BrandEnums getByValue(byte value) {
        for (BrandEnums b : BrandEnums.values()) {
            if (b.getValue() == value) {
                return b;
            }
        }
        throw new IllegalArgumentException("Invalid!");
    }
}
