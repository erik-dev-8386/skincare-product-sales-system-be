package application.havenskin.dataAccess;

import application.havenskin.models.ProductImages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponseDTO {
    private String productName;
    private int quantity;
    private double discountPrice;
    private String imageUrl;
}
