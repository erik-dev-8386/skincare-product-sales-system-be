package application.havenskin.enums;

public enum OrderEnums {
    UNORDERED((byte) 0),   // Đã thêm vào giỏ hàng nhưng chưa đặt hàng
    PENDING((byte) 1),     // Người dùng đã nhấn "Đặt hàng", chờ shop xác nhận
    PROCESSING((byte) 2),  // Shop đã xác nhận, đơn hàng đang được chuẩn bị
    SHIPPING((byte) 3),    // Đơn hàng đang được vận chuyển
    DELIVERED((byte) 4),   // Đơn hàng đã giao thành công
    CANCELLED((byte) 5),   // Đơn hàng bị hủy bởi khách hàng hoặc shop
    RETURNED((byte) 6);    // Khách hàng trả lại đơn hàng
//    PENDING((byte) 0),
//    CONFIRMED((byte) 1),
//    PROCESSING((byte) 2),
//    SHIPPED((byte) 3),
//    DELIVERED((byte) 4),
//    CANCELLED((byte) 5),
//    RETURNED((byte) 6),
//    REFUNDED((byte) 7),
//    PAYMENT_FAILED((byte) 8);
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
