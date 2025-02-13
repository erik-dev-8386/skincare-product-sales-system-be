package application.havenskin.dataAccess;

import lombok.Data;

@Data
public class AuthencationRequest {
    private String email;
    private String password;
}
