package application.havenskin.dataAccess;

import application.havenskin.models.Orders;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;

public class TransactionDTO {
    private byte transactionStatus;

    private LocalDateTime transactionTime;

    private String orderId;

    @Column(name="transaction_type", length = 50)
    private String transactionType;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false)
    private Orders orders;
}
