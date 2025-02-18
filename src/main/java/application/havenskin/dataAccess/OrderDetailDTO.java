package application.havenskin.dataAccess;

import application.havenskin.enums.OrderDetailEnums;
import application.havenskin.enums.OrderEnums;
import application.havenskin.models.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDetailDTO {

    private int quantity;

    private double discountPrice;

    private String productId;

    private Products products;

    private String orderId;

    private Orders orders;

    private Byte status = OrderDetailEnums.ACTIVE.getValue();
}
