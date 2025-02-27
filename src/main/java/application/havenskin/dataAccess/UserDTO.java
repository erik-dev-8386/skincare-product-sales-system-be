package application.havenskin.dataAccess;

import application.havenskin.models.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class UserDTO {
    private String firstName;

    private String lastName;

    private String gender;

    private String address;

    private LocalDate birthDate;

    private String email;

    private String password;

    private String phoneNumber;

    private byte rating;

    private String image;

    private byte role;

    private byte status;

    private List<Orders> orders;

    private List<ResultTests> resultTests;

    private List<Blogs> blogs;

    private List<Feedbacks> feedbacks;

    private List<SkinCaresPlan> skinCaresPlan;

    private List<CoinWallets> coinWallets;
}
