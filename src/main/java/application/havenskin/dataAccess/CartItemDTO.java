package application.havenskin.dataAccess;

import lombok.Data;

@Data
public class CartItemDTO {
    private String productName;
    private int quantity;
    private double price;
}
