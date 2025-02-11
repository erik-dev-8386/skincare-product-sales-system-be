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

    private Byte status;

    private Integer fromDistrictId;

    private Integer serviceId;

    private Integer toDistrictId;

    private String toWardCode;

    private String toName;

    private String toPhone;

    private String toAddress;

    private Integer weight;

    private Integer length;

    private Integer width;

    private Integer height;

    private Integer codAmount;

    private String note;
}
