package application.havenskin.BusinessObject.Models;

import jakarta.persistence.*;
import lombok.Data;

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
    private String blogCategoryName;

    @Column(name = "description")
    private String description;


    @OneToMany(mappedBy = "blogCategory")
    private List<Blogs> blogs;
}
