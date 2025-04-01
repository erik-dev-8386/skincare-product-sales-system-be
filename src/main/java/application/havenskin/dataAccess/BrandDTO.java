package application.havenskin.dataAccess;

import application.havenskin.enums.BrandEnums;
import application.havenskin.models.Products;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.util.List;
@Data
public class BrandDTO {
    @NotBlank(message = "Tên thương hiệu không được để trống")
    @Pattern(
//            regexp = "^[^0-9\\W][\\w\\s]+$",
            regexp = "^(?![0-9\\s])[\\p{L}0-9 ].*$",
            message = "Tên thương hiệu không được bắt đầu bằng số hoặc chứa ký tự đặc biệt"
    )
    @Size(min = 2, max = 255, message = "Tên thương hiệu phải từ 2-255 ký tự")
//    @Size(max = 255, message = "Tên thương hiệu không được vượt quá 255 ký tự")
    @Nationalized
    private String brandName;

    @Nationalized
    private String description;

    @NotBlank(message = "Quốc gia không được để trống")
//    @Pattern(regexp = "^\\p{L}[\\p{L}\\s]*$", message = "Quốc gia chỉ được chứa chữ cái và khoảng trắng")
    @Nationalized
    private String country;
    private List<Products> products;
    private Byte status = BrandEnums.ACTIVE.getValue();
}
