package application.havenskin.dataAccess;

import application.havenskin.models.Orders;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class TransactionDTO {

    private byte transactionStatus;

    private String transactionCode;

    private String content;

    private String bankName;

    private LocalDateTime transactionTime;

    private String orderId;

    private double amount;

    private byte transactionType;

    private Orders orders;
}
