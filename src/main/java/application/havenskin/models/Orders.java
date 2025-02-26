package application.havenskin.models;

import application.havenskin.enums.OrderEnums;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Orders")
@Data
public class Orders {
    @Id
    @Column(name = "order_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderId;

    @Column(name = "order_time")
    private Date orderTime;

    @NotNull
    @Column(name = "user_id", length = 50)
    private String userId;

    @NotNull
    @Column(name = "total_amount")
    private int totalAmount;

    @NotNull
    @Column(name = "status")
    private byte status;

    @Column(name = "cancel_time")
    private Date cancelTime;

    @Column(name = "address", length = 50)
    @Nationalized
    private String address;

    @Column(name = "shipment_free")
    private double shipmentFree;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private Users user;

    @JsonIgnore
    @OneToMany(mappedBy = "orders")
    private List<OrderDetails> orderDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "orders")
    private List<Shipments> shipments;

    @JsonIgnore
    @OneToOne(mappedBy = "orders")
    private Transactions transactions;
}
