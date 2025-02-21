package application.havenskin.dataAccess;

import lombok.Data;

@Data
public class AuthencationResponse {
    private String token;
    private boolean status;
}
