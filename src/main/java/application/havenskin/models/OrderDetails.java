package application.havenskin.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "OrderDetails")
@Data
public class OrderDetails {
    @Id
    @Column(name = "order_detail_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderDetailId;

    @NotNull
    @Column(name = "quantity")
    private int quantity;

    @Column(name = "discount_price")
    private double discountPrice;

    @NotNull
    @Column(name = "product_id", length = 50)
    private String productId;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    private Products products;

    @NotNull
    @Column(name = "order_id", length = 50)
    private String orderId;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false)
    private Orders orders;
}
