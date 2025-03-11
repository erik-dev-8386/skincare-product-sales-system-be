package application.havenskin.repositories;

import application.havenskin.models.BlogHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogHashtagRepository extends JpaRepository<BlogHashtag, String> {
//    BlogHashtag findByBlogHashtagName(String blogHashtagName);
@Query("SELECT h FROM BlogHashtag h WHERE h.blogHashtagName = :hashtagName")
BlogHashtag findHashtagByName(@Param("hashtagName") String hashtagName);
}
