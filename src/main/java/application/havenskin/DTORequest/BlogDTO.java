package application.havenskin.DTORequest;

import application.havenskin.BusinessObject.Models.BlogCategory;
import application.havenskin.BusinessObject.Models.BlogImages;
import application.havenskin.BusinessObject.Models.Users;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class BlogDTO {

    private String blogTitle;

    private String blogContent;

    private String userId;

    private LocalDateTime postedTime;

    private LocalDateTime deletedTime;

    private Users user;

    private List<BlogImages> blogImages;

    private BlogCategory blogCategory;
}
