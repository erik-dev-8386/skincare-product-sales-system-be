package application.havenskin.dataAccess;

import application.havenskin.enums.ProductEnums;
import application.havenskin.models.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.util.Date;
import java.util.List;
@Data
public class ProductDTO {

    @Nationalized
    private String productName;

    private double unitPrice;

    private Double discountPrice;

    @Nationalized
    private String description;

    @Nationalized
    private String ingredients;

    private int quantity;

    private Date createdTime = new Date();

    private Date deletedTime;

    private Date mfg;

    private Date exp;

    private double netWeight;

    private Byte status = ProductEnums.AVAILABLE.getValue();

    private String discountId;

   // private Discounts discounts;

    private String categoryId;

 //   private Categories categories;

    private String brandId;

   // private Brands brands;

    private String skinTypeId;

  //  private SkinTypes skinTypes;

    private List<ProductImages> productImages;

  //  private List<Feedbacks> feedbacks;
}
