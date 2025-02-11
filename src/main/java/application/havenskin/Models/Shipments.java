package application.havenskin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "Shipments")
@Data
public class Shipments {
    @Id
    @Column(name = "shipment_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String shipmentId;

    @Column(name = "shipment_code", length = 50)
    private String shipmentCode;


    @Column(name = "shipment_date", length = 50)
    private String shipmentDate;

    @Column(name = "shipment_status", length = 50)
    private String shipmentStatus;

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

    @NotNull
    @Column(name = "from_District_id")
    private Integer fromDistrictId;
    @NotNull
    @Column(name = "service_id")
    private Integer serviceId;

    @NotNull
    @Column(name = "to_district_id")
    private Integer toDistrictId;

    @NotNull
    @Column(name = "to_ward_code")
    private String toWardCode;

    @NotNull
    @Column(name = "to_name")
    private String toName;

    @NotNull
    @Column(name = "to_phone")
    private String toPhone;

    @NotNull
    @Column(name = "to_address")
    private String toAddress;

    @NotNull
    @Column(name = "weight")
    private Integer weight;

    @NotNull
    @Column(name = "length")
    private Integer length;

    @NotNull
    @Column(name = "width")
    private Integer width;

    @NotNull
    @Column(name = "height")
    private Integer height;

    @Column(name = "codAmout")
    private Integer codAmount;

    @Column(name = "note")
    private String note;
}
