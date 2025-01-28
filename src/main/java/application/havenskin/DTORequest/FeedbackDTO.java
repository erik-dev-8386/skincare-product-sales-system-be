package application.havenskin.DTORequest;

import application.havenskin.BusinessObject.Models.Products;
import application.havenskin.BusinessObject.Models.Users;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class FeedbackDTO {

    private String feedbackContent;

    private LocalDateTime feedbackDate;

    private String productId;

    private Products products;

    private String userId;

    private Users users;
}
