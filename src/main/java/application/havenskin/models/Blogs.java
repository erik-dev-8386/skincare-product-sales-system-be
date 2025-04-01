package application.havenskin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Blogs")
@Data
public class Blogs {
    @Id
    @Column(name = "blog_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String blogId;

    @Column(name = "blog_title", length = 250)
    @Nationalized
    private String blogTitle;

    @Column(name = "blog_content", length = 10000)
    @Nationalized
    private String blogContent;

//    @NotNull
//    @Column(name = "user_id", length = 50)
//    private String userId;

    @NotNull
    @Column(name = "posted_time")
    private Date postedTime;

    @Column(name = "deleted_time")
    private Date deletedTime;

//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
//    private Users user;
    @ToString.Exclude
//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ToString.Exclude
    @OneToMany(mappedBy = "blog")
    private List<BlogImages> blogImages;

//    @ManyToOne
//    @JsonIgnore
//    @JoinColumn(name = "blog_category_id", referencedColumnName = "blog_category_id", nullable = false)
//    private BlogCategory blogCategory;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "blog_category_id",nullable = false)
    private BlogCategory blogCategory;


    //không cần tạo riêng 1 enity chỉ để ánh xạ ManyToMany, vì JPA có thể xử lý trực tiếp
    //bằng  @JoinTable trong Blog Entity
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "blog_hashtag_mapping",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "blog_hashtag_id")
    )
    private List<BlogHashtag> hashtags;


    @NotNull
    @Column(name = "status")
    private byte status;
}
