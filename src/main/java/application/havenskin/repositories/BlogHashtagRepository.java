package application.havenskin.repositories;

import application.havenskin.models.BlogHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogHashtagRepository extends JpaRepository<BlogHashtag, String> {
    BlogHashtag findByBlogHashtagName(String blogHashtagName);
}
