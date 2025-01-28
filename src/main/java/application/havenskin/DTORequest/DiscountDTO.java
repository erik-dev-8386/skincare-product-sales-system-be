package application.havenskin.DTORequest;

import application.havenskin.BusinessObject.Models.Products;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

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
