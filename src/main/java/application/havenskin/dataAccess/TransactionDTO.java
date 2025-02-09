package application.havenskin.dataAccess;

import application.havenskin.models.Orders;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
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
