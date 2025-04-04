package application.havenskin.models;

import application.havenskin.enums.CategoryEnums;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.util.List;

@Entity
@Table(name = "Categories")
@Data
public class Categories {
    @Id
    @Column(name = "category_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String categoryId;

    @Column(name = "category_name", length = 50)
    @NotBlank(message = "Tên danh mục sản phẩm không được để trống!")
    @Pattern(regexp = "^(?![0-9\\s])[\\p{L}0-9 ].*$",
            message = "Tên danh mục sản phẩm không được bắt đầu bằng số hoặc chứa ký tự đặc biệt")
    @Nationalized
    private String categoryName;

    @Column(name = "description", length = 10000)
    @Nationalized
    private String description;


    @JsonIgnore
    @OneToMany(mappedBy = "categories")
    private List<Products> products;

    @NotNull
    @Column(name = "status", length = 20)
    private Byte status;
}
