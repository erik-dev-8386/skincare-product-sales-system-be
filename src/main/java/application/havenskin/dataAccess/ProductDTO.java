package application.havenskin.dataAccess;

import application.havenskin.enums.ProductEnums;
import application.havenskin.models.*;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class ProductDTO {

    private String productName;

    private double unitPrice;

    private double discountPrice;

    private String description;

    private String ingredients;

    private int quantity;

    private LocalDateTime createdTime;

    private LocalDateTime deletedTime;

    private LocalDate mfg;

    private LocalDate exp;

    private double netWeight;

    private Byte status = ProductEnums.AVAILABLE.getValue();

    private String discountId;

    private Discounts discounts;

    private String categoryId;

    private Categories categories;

    private String brandId;

    private Brands brands;

    private String skinTypeId;

    private SkinTypes skinTypes;

    private List<ProductImages> productImages;

    private List<Feedbacks> feedbacks;
}
