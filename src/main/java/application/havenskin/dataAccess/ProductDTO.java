    package application.havenskin.dataAccess;

    import application.havenskin.enums.ProductEnums;
    import application.havenskin.models.*;
    import jakarta.validation.constraints.*;
    import lombok.Data;
    import org.hibernate.annotations.Nationalized;

    import java.util.Date;
    import java.util.List;
    @Data
    public class ProductDTO {

        @NotBlank(message = "Tên sản phẩm không được để trống!")
        @Pattern(
//                regexp = "^(?![0-9])[a-zA-ZÀ-ỹ0-9 ]+$",
                regexp = "^[a-zA-ZÀ-ỹ][a-zA-ZÀ-ỹ0-9 '\\-.,&+/()]*$",                message = "Tên sản phẩm không hợp lệ. Không được bắt đầu bằng số hoặc chứa ký tự đặc biệt"
        )
        @Nationalized
        private String productName;

        @Positive(message = "Giá của sản phẩm phải là số dương!")
        @Min(value = 1, message = "Giá sản phẩm tối thiểu là 1")
        private double unitPrice;

        @PositiveOrZero(message = "Giá giảm phải là số dương!")
        private Double discountPrice;

        @NotBlank(message = "Mô tả không được để trống!")
        @Size(min = 20, message = "Mô tả phải có ít nhất 20 ký tự")
        @Nationalized
        private String description;

        @NotBlank(message = "Thành phần không được để trống!")
        @Size(min = 10, message = "Thành phần phải có ít nhất 10 ký tự")
        @Nationalized
        private String ingredients;

        @Min(value = 0, message = "Số lượng không được nhỏ hơn 0!")
        private int quantity;

    //    @PastOrPresent(message = "Ngày tạo không được ở tương lai!")
        private Date createdTime = new Date();

        @FutureOrPresent(message = "Thời gian xóa không thể ở quá khứ!")
        private Date deletedTime;

    //    @NotNull(message = "Ngày sản xuất không được để trống!")
        @PastOrPresent(message = "Ngày sản xuất phải là ngày trong quá khứ hoặc hiện tại!")
        private Date mfg;

    //    @NotNull(message = "Ngày hết hạn không được để trống!")
        @Future(message = "Ngày hết hạn phải là ngày trong tương lai!")
        private Date exp;

        @Positive(message = "Khối lượng tịnh phải là số dương!")
        @Min(value = 1, message = "Khối lượng tịnh tối thiểu là 1")
        private double netWeight;

        private Byte status = ProductEnums.AVAILABLE.getValue();

        private String discountId;

        private Discounts discounts;

        @NotBlank(message = "Hướng dẫn sử dụng không được để trống!")
        private String usageInstruction;

        private String categoryId;

        private Categories categories;

        private String brandId;

        private Brands brands;

        private String skinTypeId;

        private SkinTypes skinTypes;

        private List<ProductImages> productImages;

        private List<Feedbacks> feedbacks;
    }
