package application.havenskin.dataAccess;

import application.havenskin.models.Orders;
import lombok.Data;

@Data
public class ShipmentDTO {
    private String shipmentCode;

    private String shipmentDate;

    private String shipmentStatus;

    private String orderId;

    private Orders orders;
}
