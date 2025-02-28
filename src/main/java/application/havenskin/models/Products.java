package application.havenskin.models;

import application.havenskin.enums.ProductEnums;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @Column(name = "product_name", length = 50)
    @Nationalized
    private String productName;

    @Column(name = "unit_price")
    private double unitPrice;

    @Column(name = "discount_price")
    private Double discountPrice;

    @Column(name = "description", length = 255)
    @Nationalized
    private String description;

    @Column(name = "ingredients", length = 255)
    @Nationalized
    private String ingredients;

    @Column(name = "quantity")
    private int quantity;

    @NotNull
    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "deleted_time")
    private Date deletedTime;

    @Column(name = "mfg")
    private Date mfg;

    @Column(name = "exp")
    private Date exp;

    @Column(name = "net_weight")
    private double netWeight;

    @NotNull
    @Column(name = "status")
    private Byte status;

    @NotNull
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


    @OneToMany(mappedBy = "products")
    private List<OrderDetails> orderDetails;
}