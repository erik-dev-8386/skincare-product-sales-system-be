package application.havenskin.dataAccess;

import application.havenskin.enums.CategoryEnums;
import application.havenskin.models.Products;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.util.List;
@Data
public class CategoryDTO {

    @NotBlank(message = "Tên danh mục sản phẩm không được để trống!")
    @Pattern(regexp = "^(?![0-9\\s])[\\p{L}0-9 ].*$",
            message = "Tên danh mục sản phẩm không được bắt đầu bằng số hoặc chứa ký tự đặc biệt")
    @Nationalized
    private String categoryName;

    @Nationalized
    private String description;


    private List<Products> products;

    private Byte status = CategoryEnums.ACTIVE.getStatus();
}
