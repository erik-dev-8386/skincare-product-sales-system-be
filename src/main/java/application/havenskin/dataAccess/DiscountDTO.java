package application.havenskin.dataAccess;

import application.havenskin.models.Products;

import java.time.LocalDateTime;
import java.util.List;

public class DiscountDTO {
    private String discountName;

    private String discountCode;

    private String description;

    private LocalDateTime createdTime;

    private LocalDateTime deletedTime;

    private LocalDateTime actualStartTime;

    private LocalDateTime actualEndTime;

    private double discountPercent;

    private byte status;

    private List<Products> products;
}
