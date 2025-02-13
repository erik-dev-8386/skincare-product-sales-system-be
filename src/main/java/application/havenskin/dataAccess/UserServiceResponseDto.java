package application.havenskin.dataAccess;

import application.havenskin.models.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceResponseDto {
    private boolean isSucceed;
    private String message;
    private List<Users> users;
}
