package application.havenskin.dataAccess;

import application.havenskin.models.Orders;
import application.havenskin.models.Products;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class    OrderDetailDTO {

    private int quantity;

    private double discountPrice;

    private String productId;

    private Products products;

    private String orderId;

    private Orders orders;

    @NotNull
    @Column(name = "status")
    private Byte status;
}
