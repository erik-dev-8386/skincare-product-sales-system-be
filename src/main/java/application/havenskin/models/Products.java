package application.havenskin.models;

import application.havenskin.enums.ProductEnums;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Products")
@Data
public class  Products {
    @Id
    @Column(name = "product_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String productId;

    @NotBlank(message = "Tên sản phẩm không được để trống!")
    @Pattern(
            regexp = "^[a-zA-ZÀ-ỹ][a-zA-ZÀ-ỹ0-9 '\\-.,&+/()]*$",            message = "Tên sản phẩm không hợp lệ. Không được bắt đầu bằng số hoặc chứa ký tự đặc biệt"
    )
    @Column(name = "product_name", length = 10000)
    @Nationalized
    private String productName;

    @NotBlank(message = "Hướng dẫn sử dụng không được để trống!")
    @Column(name = "usage_instruction", length = 10000)
    @Nationalized
    private String usageInstruction;

    @Positive(message = "Giá của sản phẩm phải là số dương!")
    @Column(name = "unit_price")
    private double unitPrice;

    @PositiveOrZero(message = "Giá giảm phải là số dương!")
    @Column(name = "discount_price")
    private Double discountPrice;

    @NotBlank(message = "Mô tả không được để trống!")
    @Column(name = "description", length = 10000)
    @Nationalized
    private String description;

    @NotBlank(message = "Thành phần không được để trống!")
    @Column(name = "ingredients", length = 10000)
    @Nationalized
    private String ingredients;

    @Min(value = 0, message = "Số lượng không được nhỏ hơn 0!")
    @Column(name = "quantity")
    private int quantity;

//    @PastOrPresent(message = "Ngày tạo không được ở tương lai!")
    @NotNull
    @Column(name = "created_time")
    private Date createdTime;

   @FutureOrPresent(message = "Thời gian xóa không thể ở quá khứ!")
    @Column(name = "deleted_time")
    private Date deletedTime;

//    @NotNull(message = "Ngày sản xuất không được để trống!")
    @PastOrPresent(message = "Ngày sản xuất phải là ngày trong quá khứ hoặc hiện tại!")
    @Column(name = "mfg")
    private Date mfg;

//    @NotNull(message = "Ngày hết hạn không được để trống!")
    @Future(message = "Ngày hết hạn phải là ngày trong tương lai!")

    @Column(name = "exp")
    private Date exp;

    @Positive(message = "Khối lượng tịnh phải là số dương!")
    @Column(name = "net_weight")
    private double netWeight;

    @NotNull
    @Column(name = "status")
    private Byte status;

  //  @NotNull
    @Column(name = "discount_id", length = 50)
    private String discountId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "discount_id", referencedColumnName = "discount_id", insertable = false, updatable = false)
    private Discounts discounts;

    @NotNull
    @Column(name = "category_id", length = 50)
    private String categoryId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", insertable = false, updatable = false)
    private Categories categories;

    @NotNull
    @Column(name = "brand_id", length = 50)
    private String brandId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "brand_id", insertable = false, updatable = false)
    private Brands brands;

    @NotNull
    @Column(name = "skin_type_id", length = 50)
    private String skinTypeId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "skin_type_id", referencedColumnName = "skin_type_id", insertable = false, updatable = false)
    private SkinTypes skinTypes;

    @OneToMany(mappedBy = "products")
    private List<ProductImages> productImages;

    @JsonIgnore
    @OneToMany(mappedBy = "products")
    private List<Feedbacks> feedbacks;

    @Column(name = "sold_quantity")
    private int soldQuantity;
}