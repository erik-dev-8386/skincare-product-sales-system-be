package application.havenskin.enums;

public enum ProductEnums {
    AVAILABLE((byte) 1),
    OUT_OF_STOCK((byte) 2),
    DELETE((byte) 3);

    private final byte value;

    ProductEnums(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
    public static ProductEnums fromValue(byte value) {
        for (ProductEnums x : ProductEnums.values()) {
            if (x.getValue() == value) {
                return x;
            }
        }
        throw new IllegalArgumentException("No ProductEnums with value " + value);
    }
}
