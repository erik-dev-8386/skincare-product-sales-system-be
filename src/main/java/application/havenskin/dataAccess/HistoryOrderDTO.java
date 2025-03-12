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
    private List<String> productName;
    private int quantity;
}
