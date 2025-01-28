package application.havenskin.DTORequest;

import application.havenskin.BusinessObject.Models.Orders;
import application.havenskin.BusinessObject.Models.Products;


public class OrderDetailDTO {
    private int quantity;

    private double discountPrice;

    private String productId;

    private Products products;

    private String orderId;

    private Orders orders;
}
