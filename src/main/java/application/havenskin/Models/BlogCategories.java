package application.havenskin.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "BlogCategories")
@Data
public class BlogCategories {
    @Id
    @Column(name = "blog_category_id", length = 50)
    private String blogCategoryId;

    @Column(name = "category_name", length = 50)
    private String categoryName;

    @Column(name = "description", length = 250)
    private String description;

    @OneToMany(mappedBy = "blogCategories")
    private List<Blogs> blogs;

}
