package application.havenskin.dataAccess;

import application.havenskin.models.Orders;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShipmentDTO {

    private String shipmentCode;

    private String shipmentDate;

    private String shipmentStatus;

    private String orderId;

    private Orders orders;

    private Byte status;
}
