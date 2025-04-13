package application.havenskin.dataAccess;

import application.havenskin.models.OrderDetails;
import application.havenskin.models.Transactions;
import application.havenskin.models.Users;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.util.Date;
import java.util.List;
@Data
public class OrderDTO {
    private Date orderTime;
    private String userId;
    private double totalAmount;
    private byte status;
    private Date cancelTime;
    @Nationalized
    private String address;
    private double shipmentFree;
    private String content;
    private Users user;
    private List<OrderDetails> orderDetails;
    private Transactions transactions;
    private String customerName;
    private String customerPhone;

}
