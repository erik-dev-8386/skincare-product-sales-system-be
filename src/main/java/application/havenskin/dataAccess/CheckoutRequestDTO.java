package application.havenskin.dataAccess;

import lombok.Data;

import java.util.List;
@Data
public class CheckoutRequestDTO {
    private String email;
    private List<CartItemRequest> cartItemRequests;
    private double totalPrice;
}
