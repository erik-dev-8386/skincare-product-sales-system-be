package application.havenskin.DataAccess;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthServiceResponseDto {
    private boolean isSucceed;
    private String message;
    private byte role;
}
