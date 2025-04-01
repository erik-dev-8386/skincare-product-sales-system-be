package application.havenskin.dataAccess;

import application.havenskin.enums.DiscountEnum;
import application.havenskin.models.Products;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.util.Date;
import java.util.List;
@Data
public class DiscountDTO {

    @NotBlank(message = "Tên khuyến mãi không được để trống")
    @Pattern(
            regexp = "^(?![0-9\\s])[\\p{L}0-9 ].*$",
            message = "Tên khuyến mãi không được bắt đầu bằng số hoặc chứa ký tự đặc biệt"
    )
    @Size(max = 255, message = "Tên khuyến mãi không được vượt quá 255 ký tự")
    @Nationalized
    private String discountName;

    @NotBlank(message = "Mã giảm giá không được để trống")
    @Pattern(
            regexp = "^[A-Za-z0-9_-]+$",
            message = "Mã giảm giá chỉ được chứa chữ cái, số, gạch ngang (-) hoặc gạch dưới (_)"
    )
    @Size(max = 50, message = "Mã giảm giá không được vượt quá 50 ký tự")
    private String discountCode;

    @Nationalized
    private String description;

//    @PastOrPresent(message = "Ngày tạo phải là ngày hiện tại hoặc trong quá khứ")
    private Date createdTime = new Date();

    @FutureOrPresent(message = "Ngày xóa phải lớn hơn hoặc bằng ngày hiện tại")
    private Date deletedTime;

    @FutureOrPresent(message = "Thời gian bắt đầu phải từ hiện tại trở đi")
    private Date actualStartTime;

    @Future(message = "Thời gian kết thúc phải lớn hơn thời gian bắt đầu")
    private Date actualEndTime;

    @Min(value = 0, message = "Phần trăm giảm giá không được nhỏ hơn 0")
    @Max(value = 100, message = "Phần trăm giảm giá không được lớn hơn 100")
    private double discountPercent;

    private byte status = DiscountEnum.ACTIVE.getDiscount_status();

    private List<Products> products;
}

