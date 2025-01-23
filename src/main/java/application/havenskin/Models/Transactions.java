package application.havenskin.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Transactions")
@Data
public class Transactions {
    @Id
    @Column(name = "transaction_id", length = 50)
    private String transactionId;

    @Column(name = "transaction_status")
    private byte transactionStatus;

    @Column(name = "transaction_time")
    private LocalDateTime transactionTime;

    @Column(name = "type")
    private byte type;

    @NotNull
    @Column(name = "order_id", length = 50)
    private String orderId;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false)
    private Orders orders;
}
