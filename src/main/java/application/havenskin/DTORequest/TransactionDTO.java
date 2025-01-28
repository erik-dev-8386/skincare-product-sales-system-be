package application.havenskin.DTORequest;

import application.havenskin.BusinessObject.Models.Orders;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

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
