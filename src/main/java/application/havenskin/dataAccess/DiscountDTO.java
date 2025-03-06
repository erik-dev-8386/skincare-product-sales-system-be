package application.havenskin.dataAccess;

import application.havenskin.enums.DiscountEnum;
import application.havenskin.models.Products;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.util.Date;
import java.util.List;
@Data
public class DiscountDTO {

    @Nationalized
    private String discountName;

    private String discountCode;

    @Nationalized
    private String description;

    private Date createdTime = new Date();

    private Date deletedTime;

    private Date actualStartTime;

    private Date actualEndTime;

    private double discountPercent;

    private byte status = DiscountEnum.ACTIVE.getDiscount_status();

    private List<Products> products;
}

