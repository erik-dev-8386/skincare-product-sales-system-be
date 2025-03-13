package application.havenskin.dataAccess;

import application.havenskin.models.Products;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class HistoryOrderDTO {
    private String orderId;
    private Date orderTime;
    private double totalAmount;
    private List<ProductDetailsDTO> productName;
    private int quantity;
    private Byte status;
}
