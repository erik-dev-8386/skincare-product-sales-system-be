package application.havenskin.dataAccess;

import application.havenskin.models.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.util.Date;
import java.util.List;
@Data
public class UserDTO {
    @Nationalized
    private String firstName;

    @Nationalized
    private String lastName;

    private String gender;

    @Nationalized
    private String address;

    private Date birthDate;

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
}
