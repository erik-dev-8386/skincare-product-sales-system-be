package application.havenskin.DataAccess;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
    private String userId;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String address;
    private LocalDate birthDate;
    private String email;
    private int phoneNumber;
    private byte rating;
    private String image;
    private byte role;
    private byte status;
}
