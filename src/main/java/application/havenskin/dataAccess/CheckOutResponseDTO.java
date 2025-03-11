package application.havenskin.dataAccess;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckOutResponseDTO {
    private String orderId;
    private List<CartItemResponseDTO> cartItems;
    private double total;
}
