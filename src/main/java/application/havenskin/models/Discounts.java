package application.havenskin.models;

import application.havenskin.enums.DiscountEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Discounts")
@Data
public class Discounts {

    @Id
    @Column(name = "discount_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String discountId;

    @NotBlank(message = "Tên khuyến mãi không được để trống")
    @Pattern(
            regexp = "^(?![0-9\\s])[\\p{L}0-9 ].*$",
            message = "Tên khuyến mãi không được bắt đầu bằng số hoặc chứa ký tự đặc biệt"
    )
//    @Size(max = 255, message = "Tên khuyến mãi không được vượt quá 255 ký tự")
    @Size(min = 2, max = 255, message = "Tên thương hiệu phải từ 2-255 ký tự")
    @Column(name = "discount_name", length = 255)
    @Nationalized
    private String discountName;

    @NotBlank(message = "Mã giảm giá không được để trống")
    @Column(name = "discount_code", length = 50)
    private String discountCode;


    @Column(name = "description", length = 10000)
    @Nationalized
    private String description;

    //@PastOrPresent(message = "Ngày tạo phải là ngày hiện tại hoặc trong quá khứ")
    @NotNull
    @Column(name = "created_time")
    private Date createdTime;

    //@FutureOrPresent(message = "Ngày xóa phải lớn hơn hoặc bằng ngày hiện tại")
    @Column(name = "closed_time")
    private Date deletedTime;

    //@FutureOrPresent(message = "Thời gian bắt đầu phải từ hiện tại trở đi")
   // @NotNull
    @Column(name = "actual_start_time")
    private Date actualStartTime;

   // @Future(message = "Thời gian kết thúc phải lớn hơn thời gian bắt đầu")
    @Column(name = "actual_end_time")
    private Date actualEndTime;

    @Min(value = 0, message = "Phần trăm giảm giá không được nhỏ hơn 0")
    @Max(value = 100, message = "Phần trăm giảm giá không được lớn hơn 100")
    @Column(name = "discount_percent")
    private double discountPercent;

    @NotNull
    @Column(name = "status")
    private byte status;

    @JsonIgnore
    @OneToMany(mappedBy = "discounts")
    private List<Products> products;
}
