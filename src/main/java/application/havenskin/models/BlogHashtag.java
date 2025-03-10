package application.havenskin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "BlogHashtag")
@Data
public class BlogHashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "blog_hashtag_id", length = 50)
    private String blogHashtagId;

    @Column(name = "blog_hashtag_name", nullable = false, unique = true)
    private String blogHashtagName;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "hashtags")
    @JsonIgnore
    private Set<Blogs> blogs;

    @Column(name = "status")
    private byte status;
}
