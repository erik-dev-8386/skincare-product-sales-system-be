package application.havenskin.models;

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

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false)
    private Orders orders;
}
