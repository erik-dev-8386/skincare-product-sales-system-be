package application.havenskin.dataAccess;

import application.havenskin.models.ProductImages;
import application.havenskin.models.Products;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemsDTO {
    private String productName;
    private int quantity;
    private double discountPrice;
    private List<ProductImages> productImageUrl;
}
