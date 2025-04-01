package application.havenskin.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "BlogImages")
@Data
public class BlogImages {
    @Id
    @Column(name = "image_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String imageId;

    @Column(name = "image_url", length = 10000)
    private String imageURL;

   // @Column(name = "description", length = 500)
   // private String description;

    @NotNull
    @JsonIgnore
    @Column(name = "blog_id", length = 50)
    private String blogId;

    @ManyToOne
    @JoinColumn(name = "blog_id", referencedColumnName = "blog_id", insertable = false, updatable = false)
    @JsonIgnore
    private Blogs blog;
}
