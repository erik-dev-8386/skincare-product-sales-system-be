package application.havenskin.dataAccess;

import application.havenskin.models.OrderDetails;
import application.havenskin.models.Shipments;
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

    private Users user;

    private List<OrderDetails> orderDetails;

    private List<Shipments> shipments;

    private Transactions transactions;
}
