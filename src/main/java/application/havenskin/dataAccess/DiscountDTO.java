package application.havenskin.dataAccess;

import application.havenskin.enums.DiscountEnum;
import application.havenskin.models.Products;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class DiscountDTO {

    private String discountName;

    private String discountCode;

    private String description;

    private LocalDateTime createdTime;

    private LocalDateTime deletedTime;

    private LocalDateTime actualStartTime;

    private LocalDateTime actualEndTime;

    private double discountPercent;

    private byte status = DiscountEnum.ACTIVE.getDiscount_status();

    private List<Products> products;
}

