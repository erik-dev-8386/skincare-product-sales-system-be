package application.havenskin.dataAccess;

import application.havenskin.models.BlogCategory;
import application.havenskin.models.BlogImages;
import application.havenskin.models.Users;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class BlogDTO {
    private String blogId;

    private String blogTitle;

    private String blogContent;

    private String userId;

    private LocalDateTime postedTime;

    private LocalDateTime deletedTime;

    private Users user;

    private List<BlogImages> blogImages;

    private BlogCategory blogCategory;
}
