package application.havenskin.dataAccess;

import application.havenskin.models.BlogCategory;
import application.havenskin.models.BlogImages;
import application.havenskin.models.Users;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.util.Date;
import java.util.List;
@Data
public class BlogDTO {
    private String blogId;
    @Nationalized
    private String blogTitle;
    @Nationalized
    private String blogContent;

    private String userId;

    private Date postedTime;

    private Date deletedTime;

    private Users user;

    private List<BlogImages> blogImages;

    private BlogCategory blogCategory;
}
