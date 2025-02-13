package application.havenskin.dataAccess;

import application.havenskin.models.Orders;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TransactionDTO {

    private byte transactionStatus;

    private LocalDateTime transactionTime;

    private String orderId;

    private String transactionType;

    private Orders orders;
}
