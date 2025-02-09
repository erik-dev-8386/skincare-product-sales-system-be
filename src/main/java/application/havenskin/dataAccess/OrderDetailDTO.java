package application.havenskin.dataAccess;

import application.havenskin.models.Orders;
import application.havenskin.models.Products;
import lombok.Data;

@Data
public class OrderDetailDTO {
    private int quantity;

    private double discountPrice;

    private String productId;

    private Products products;

    private String orderId;

    private Orders orders;
}
