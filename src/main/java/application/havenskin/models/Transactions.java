package application.havenskin.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDateTime;

@Entity
@Table(name = "Transactions")
@Data
public class Transactions {
    @Id
    @Column(name = "transaction_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionId;

    @Column(name = "transaction_status")
    private byte transactionStatus;

    @Column(name = "transaction_time")
    private LocalDateTime transactionTime;

    @NotNull
    @Column(name = "order_id", length = 50)
    private String orderId;

    @Column(name="transaction_type", length = 50)
    @Nationalized
    private String transactionType;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false)
    private Orders orders;
}
