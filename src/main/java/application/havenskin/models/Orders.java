package application.havenskin.models;

import application.havenskin.enums.OrderEnums;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Orders")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @Column(name = "total_amount")
    private double totalAmount;

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
    @OneToMany(mappedBy = "orders")
    private List<Shipments> shipments;

    @JsonIgnore
    @OneToOne(mappedBy = "orders")
    private Transactions transactions;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private Users users;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails;

    @Column(name = "customer_name", length = 100)
    @Nationalized
    private String customerName;

    @Column(name = "customer_phone", length = 15)
    private String customerPhone;
}
