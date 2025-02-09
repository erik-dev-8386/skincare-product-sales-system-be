package application.havenskin.dataAccess;

import application.havenskin.models.Products;
import application.havenskin.models.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class FeedbackDTO {

    private String feedbackContent;

    private LocalDateTime feedbackDate;

    private String productId;

    private Products products;

    private String userId;

    private Users users;

    private byte status;
}
