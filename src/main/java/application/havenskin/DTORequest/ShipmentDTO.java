package application.havenskin.DTORequest;

import application.havenskin.BusinessObject.Models.Orders;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

public class ShipmentDTO {
    private String shipmentCode;

    private String shipmentDate;

    private String shipmentStatus;

    private String orderId;

    private Orders orders;
}
