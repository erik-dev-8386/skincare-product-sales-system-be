package application.havenskin.dataAccess;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentDTO {
    private String expectedDeliveryTime;

    private Double fee;

    private String coupon;

    private Double insurance;

    private Double mainService;

    private Double r2s;

    private Double returnFee;

    private Double stationDo;

    private Double stationPu;

    private String orderCode;

    private String sortCode;

    private Double totalFee;

    private String transType;
}
