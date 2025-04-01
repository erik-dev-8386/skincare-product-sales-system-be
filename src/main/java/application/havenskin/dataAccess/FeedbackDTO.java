package application.havenskin.dataAccess;

import application.havenskin.enums.FeedBackEnum;
import application.havenskin.models.Products;
import application.havenskin.models.Users;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.util.Date;

@Data
public class FeedbackDTO {

    @Nationalized
    private String feedbackContent;

    private Date feedbackDate = new Date();

    private String productId;

    private Products products;

    private String userId;

    private Users users;

    private byte status = FeedBackEnum.ACTIVE.getFeedBack_status();

    private byte rating;

}
