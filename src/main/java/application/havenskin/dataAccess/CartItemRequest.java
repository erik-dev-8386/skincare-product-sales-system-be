package application.havenskin.dataAccess;

import lombok.Data;

@Data
public class CartItemRequest {
    private String productName;
    private int quantity;
    private double price;
}
