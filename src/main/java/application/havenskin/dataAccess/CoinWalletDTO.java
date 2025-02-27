package application.havenskin.dataAccess;

import application.havenskin.models.Users;
import lombok.Data;

@Data
public class CoinWalletDTO {
    private String coinWalletId;
    private double balance;
    private byte status;
    private String userId;
    private Users user;
}
