package application.havenskin.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "BlogImages")
@Data
public class BlogImages {
    @Id
    @Column(name = "image_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String imageId;

    @Column(name = "image_url", length = 255)
    private String imageURL;

    @Column(name = "description", length = 500)
    @Nationalized
    private String description;

    @NotNull
    @Column(name = "blog_id", length = 50)
    private String blogId;

    @ManyToOne
    @JoinColumn(name = "blog_id", referencedColumnName = "blog_id", insertable = false, updatable = false)
    private Blogs blog;
}
