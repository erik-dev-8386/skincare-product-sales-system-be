package application.havenskin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;

import java.util.List;

@Entity
@Table(name = "BlogCategory")
@Data
public class BlogCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "blog_category_id", length = 50)
    private String blogCategoryId;

    @Column(name = "blog_category_name", length = 50)
    @Nationalized
    private String blogCategoryName;

    @Column(name = "description")
    @Nationalized
    private String description;


    @ToString.Exclude
    @OneToMany(mappedBy = "blogCategory")
    @JsonIgnore
    private List<Blogs> blogs;

    @Column(name = "status")
    private byte status;
}
