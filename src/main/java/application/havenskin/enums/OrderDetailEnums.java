package application.havenskin.enums;

public enum OrderDetailEnums {
    INACTIVE((byte) 0),
    ACTIVE((byte) 1);
    private final byte value;

    OrderDetailEnums(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

   public static OrderDetailEnums fromValue(byte value) {
        for (OrderDetailEnums orderDetailEnums : OrderDetailEnums.values()) {
            if (orderDetailEnums.getValue() == value) {
                return orderDetailEnums;
            }
        }
        throw new IllegalArgumentException("Invalid status value: " + value);
   }
}
