package application.havenskin.dataAccess;

import lombok.Data;

@Data
public class ProductDetailsDTO {
    private String productName;
    private int quantity;
    private double discountPrice;
    private String imageUrl;
}
