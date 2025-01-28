package application.havenskin.DTORequest;

import application.havenskin.BusinessObject.Models.OrderDetails;
import application.havenskin.BusinessObject.Models.Shipments;
import application.havenskin.BusinessObject.Models.Transactions;
import application.havenskin.BusinessObject.Models.Users;


import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private LocalDateTime orderTime;

    private String userId;

    private int totalAmount;

    private byte status;

    private LocalDateTime cancelTime;

    private String address;

    private double shipmentFree;

    private Users user;

    private List<OrderDetails> orderDetails;

    private List<Shipments> shipments;

    private Transactions transactions;

}
