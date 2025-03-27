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
            regexp = "^[^0-9\\W][\\w\\s]+$",
            message = "Tên thương hiệu không được bắt đầu bằng số hoặc chứa ký tự đặc biệt"
    )
    @Size(max = 255, message = "Tên thương hiệu không được vượt quá 255 ký tự")
    @Nationalized
    private String brandName;

    @Nationalized
    private String description;

    @NotBlank(message = "Quốc gia không được để trống")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Quốc gia chỉ được chứa chữ cái và khoảng trắng")
    @Nationalized
    private String country;
    private List<Products> products;
    private Byte status = BrandEnums.ACTIVE.getValue();
}
