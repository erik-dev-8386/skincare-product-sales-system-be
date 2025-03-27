package application.havenskin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.firebase.database.DatabaseError;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.checkerframework.checker.units.qual.N;
import org.checkerframework.common.aliasing.qual.Unique;
import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Transactions")
@Data
public class Transactions {
    @Id
    @Column(name = "transaction_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionId;

    @Unique
    @Column(name = "transaction_code", length = 50)
    private String transactionCode;

    @Column(name = "content", length = 255)
    @Nationalized
    private String content;

    @Column(name = "bank_name", length = 50)
    private String bankName;

    @Column(name = "transaction_status")
    private byte transactionStatus;

    @Column(name = "transaction_time")
    private LocalDateTime transactionTime;

    @NotNull
    @Column(name = "amount")
    private double amount;

    @JsonIgnore
    @NotNull
    @Column(name = "order_id", length = 50)
    private String orderId;

    @Column(name="transaction_type")
    private byte transactionType;


    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false)
    private Orders orders;
}
