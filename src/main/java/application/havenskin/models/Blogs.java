package application.havenskin.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Blogs")
@Data
public class Blogs {
    @Id
    @Column(name = "blog_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String blogId;

    @Column(name = "blog_title", length = 50)
    private String blogTitle;

    @Column(name = "blog_content", length = 50)
    private String blogContent;

    @NotNull
    @Column(name = "user_id", length = 50)
    private String userId;

    @NotNull
    @Column(name = "posted_time")
    private LocalDateTime postedTime;

    @Column(name = "deleted_time")
    private LocalDateTime deletedTime;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private Users user;

    @OneToMany(mappedBy = "blog")
    private List<BlogImages> blogImages;

    @ManyToOne
    @JoinColumn(name = "blog_category_id", referencedColumnName = "blog_category_id", nullable = false)
    private BlogCategory blogCategory;
}
