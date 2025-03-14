package application.havenskin.models;

import application.havenskin.enums.CoinWalletEnums;
import application.havenskin.enums.OrderEnums;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "CoinWallets")
@Data
public class CoinWallets {
    @Id
    @Column(name = "coin_wallet_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String coinWalletId;

    @Column(name = "balance")
    private double balance;

    @NotNull
    @Column(name = "status")
    private byte status = CoinWalletEnums.ACTIVE.getValue();

    @NotNull
    @Column(name = "user_id", length = 50)
    private String userId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private Users user;
}
