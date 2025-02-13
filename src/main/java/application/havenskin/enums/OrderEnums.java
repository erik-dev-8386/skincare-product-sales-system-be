package application.havenskin.enums;

public enum OrderEnums {
    PENDING((byte) 0),
    CONFIRMED((byte) 1),
    PROCESSING((byte) 2),
    SHIPPED((byte) 3),
    DELIVERED((byte) 4),
    CANCELLED((byte) 5),
    RETURNED((byte) 6),
    REFUNDED((byte) 7),
    PAYMENT_FAILED((byte) 8);
    private final byte order_status;

    OrderEnums(byte order_status) {
        this.order_status = order_status;
    }

    public byte getOrder_status() {
        return order_status;
    }
    public static OrderEnums fromOrderStatus(byte order_status) {
        for (OrderEnums order : OrderEnums.values()) {
            if (order.getOrder_status() == order_status) {
                return order;
            }
        }
        throw new IllegalArgumentException("Invalid order status: " + order_status);
    }
}
