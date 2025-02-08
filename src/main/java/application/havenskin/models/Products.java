package application.havenskin.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Products")
@Data
public class Products {
    @Id
    @Column(name = "product_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String productId;

    @Column(name = "product_name", length = 50)
    private String productName;

    @Column(name = "unit_price")
    private double unitPrice;

    @Column(name = "discount_price")
    private double discountPrice;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "ingredients", length = 255)
    private String ingredients;

    @Column(name = "quantity")
    private int quantity;

    @NotNull
    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "deleted_time")
    private LocalDateTime deletedTime;

    @Column(name = "mfg")
    private LocalDate mfg;

    @Column(name = "exp")
    private LocalDate exp;

    @Column(name = "net_weight")
    private double netWeight;

    @Column(name = "status", columnDefinition = "BYTE DEFAULT 1") //1: Active, 0: Deleted
    private byte status = 1;

    @NotNull
    @Column(name = "discount_id", length = 50)
    private String discountId;

    @ManyToOne
    @JoinColumn(name = "discount_id", referencedColumnName = "discount_id", insertable = false, updatable = false)
    private Discounts discounts;

    @NotNull
    @Column(name = "category_id", length = 50)
    private String categoryId;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", insertable = false, updatable = false)
    private Categories categories;

    @NotNull
    @Column(name = "brand_id", length = 50)
    private String brandId;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "brand_id", insertable = false, updatable = false)
    private Brands brands;

    @NotNull
    @Column(name = "skin_type_id", length = 50)
    private String skinTypeId;

    @ManyToOne
    @JoinColumn(name = "skin_type_id", referencedColumnName = "skin_type_id", insertable = false, updatable = false)
    private SkinTypes skinTypes;

    @OneToMany(mappedBy = "products")
    private List<ProductImages> productImages;

    @OneToMany(mappedBy = "products")
    private List<Feedbacks> feedbacks;
}