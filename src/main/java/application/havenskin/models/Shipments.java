package application.havenskin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "Shipments")
public class Shipments {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "shipment_id")
    private String shipmentId;

    @Column(name = "expected_delivery_time")
    private String expectedDeliveryTime;

    @Column(name = "fee")
    private Double fee;

    @Column(name = "coupon")
    private String coupon;

    @Column(name = "insurance")
    private Double insurance;

    @Column(name = "main_service")
    private Double mainService;

    @Column(name = "r2s")
    private Double r2s;

    @Column(name = "returnFee")
    private Double returnFee;

    @Column(name = "station_do")
    private Double stationDo;

    @Column(name = "station_pu")
    private Double stationPu;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "sort_code")
    private String sortCode;

    @Column(name = "total_fee")
    private Double totalFee;

    @Column(name = "trans_type")
    private String transType;

    @NotNull
    @Column(name = "order_id", length = 50)
    private String orderId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false)
    private Orders orders;

    @NotNull
    @Column(name = "status")
    private Byte status;
}
