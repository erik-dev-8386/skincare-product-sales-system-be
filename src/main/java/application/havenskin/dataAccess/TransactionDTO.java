package application.havenskin.dataAccess;

import application.havenskin.models.Orders;
import lombok.Data;

import java.util.Date;

@Data
public class TransactionDTO {

    private byte transactionStatus;

    private Date transactionTime;

    private String orderId;

    private String transactionType;

    private Orders orders;
}
