package application.havenskin.enums;

public enum ShipmentEnums {
    PICKED_UP((byte) 1),
    IN_TRANSIT((byte) 2),
    OUT_FOR_DELIVERY((byte) 3),
    SUCCESS_DELIVERY((byte) 4),
    FAILED_DELIVERY((byte) 5);
    private final byte shipment_status;

    ShipmentEnums(byte shipment_status) {
        this.shipment_status = shipment_status;
    }

    public byte getShipment_status() {
        return shipment_status;
    }
    public static ShipmentEnums fromShipment_status(byte shipment_status) {
        for (ShipmentEnums shipment_enum : ShipmentEnums.values()) {
            if (shipment_enum.getShipment_status() == shipment_status) {
                return shipment_enum;
            }
        }
        throw new IllegalArgumentException("Invalid Shipment Status");
    }
}
