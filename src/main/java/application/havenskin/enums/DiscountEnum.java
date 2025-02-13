package application.havenskin.enums;

public enum DiscountEnum {
    EXPIRED((byte) 0),
    UPCOMING((byte) 1),
    ACTIVE((byte) 2),
    DISABLED((byte) 3);

    private final byte discount_status;

    DiscountEnum(byte discount_status) {
        this.discount_status = discount_status;
    }

    public byte getDiscount_status() {
        return discount_status;
    }

    public static DiscountEnum fromDiscount_status(byte discount_status) {
        for (DiscountEnum discountEnum : DiscountEnum.values()) {
            if (discountEnum.getDiscount_status() == discount_status) {
                return discountEnum;
            }
        }
        throw new IllegalArgumentException("Invalid discount status: " + discount_status);
    }
}
