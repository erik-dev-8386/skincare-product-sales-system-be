package application.havenskin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "BlogHashtag")
@Data
public class BlogHashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "blog_hashtag_id", length = 50)
    private String blogHashtagId;

    @Nationalized
    @Column(name = "blog_hashtag_name", nullable = false, unique = true)
    private String blogHashtagName;

    @Nationalized
    @Column(name = "description")
    private String description;

    @ToString.Exclude
    @Nationalized
    @ManyToMany(mappedBy = "hashtags")
    @JsonIgnore
    private List<Blogs> blogs;

    @Nationalized
    @Column(name = "status")
    private byte status;
}
