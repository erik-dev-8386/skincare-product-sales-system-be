package application.havenskin.models;

import application.havenskin.enums.BrandEnums;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.util.List;

@Entity
@Table(name = "Brands")
@Data
public class Brands {
    @Id
    @Column(name = "brand_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String brandId;

    @NotBlank(message = "Tên thương hiệu không được để trống!")
    @Pattern(
            regexp = "^(?![0-9\\s])[\\p{L}0-9 ].*$",
//            regexp = "^[a-zA-ZÀ-ỹ][a-zA-ZÀ-ỹ0-9 ]*$",
            message = "Tên sản phẩm không hợp lệ. Không được bắt đầu bằng số hoặc chứa ký tự đặc biệt"
    )

    //@Size(max = 255, message = "Tên thương hiệu không được vượt quá 255 ký tự!")
    @Size(min = 3, max = 255, message = "Tên thương hiệu phải từ 2-255 ký tự")
    @Column(name = "brand_name", length = 255)
    @Nationalized
    private String brandName;

    @Column(name = "description", length = 10000)
    @Nationalized
    private String description;

    @NotBlank(message = "Quốc gia không được để trống")
//    @Pattern(regexp = "^\\p{L}[\\p{L}\\s]*$", message = "Quốc gia chỉ được chứa chữ cái và khoảng trắng")
    @Column(name = "country", length = 50)
    @Nationalized
    private String country;

    @JsonIgnore
    @OneToMany(mappedBy = "brands")
    private List<Products> products;

    @NotNull
    @Column(name = "status", length = 20)
    private Byte status;
}
