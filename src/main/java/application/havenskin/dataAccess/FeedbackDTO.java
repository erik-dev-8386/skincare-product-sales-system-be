package application.havenskin.dataAccess;

import application.havenskin.models.Products;
import application.havenskin.models.Users;

import java.time.LocalDateTime;

public class FeedbackDTO {

    private String feedbackContent;

    private LocalDateTime feedbackDate;

    private String productId;

    private Products products;

    private String userId;

    private Users users;
}
