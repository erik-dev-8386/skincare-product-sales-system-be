package application.havenskin.models;

import application.havenskin.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Users")
@Data
public class Users {

    @Id
    @Column(name = "user_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    @NotNull
    @Column(name = "first_name", length = 50)
    @Nationalized
    private String firstName;

    @NotNull
    @Column(name = "last_name", length = 50)
    @Nationalized
    private String lastName;

    @Column(name = "gender", length = 10)
    @Nationalized
    private String gender;

    @Column(name = "address", length = 50)
    @Nationalized
    private String address;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "password",length = 100)
    private String password;

    @Column(name = "phone_number", length = 15)
    private String phone;

    @Max(5)
    @Column(name = "rating")
    private byte rating;

    @Column(name = "image", length = 1000)
    private String image;

    @NotNull
    @Column(name = "role")
    private byte role;

    @Column(name = "status")
    private byte status = Role.Status.ACTIVE.getStatus();

    @JsonIgnore
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    // Đảm bảo khi xóa user thì xóa luôn đơn hàng
    private List<Orders> orders;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ResultTests> resultTests;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Blogs> blogs;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "users")
    private List<Feedbacks> feedbacks;

//    @ToString.Exclude
//    @JsonIgnore
//    @OneToMany(mappedBy = "user")
//    private List<SkinCaresPlan> skinCaresPlan;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<CoinWallets> coinWallets;

}
