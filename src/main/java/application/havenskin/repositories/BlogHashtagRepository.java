package application.havenskin.repositories;

import application.havenskin.models.BlogHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogHashtagRepository extends JpaRepository<BlogHashtag, String> {
//    BlogHashtag findByBlogHashtagName(String blogHashtagName);
//@Query("SELECT h FROM BlogHashtag h WHERE h.blogHashtagName = :hashtagName")
//Optional<BlogHashtag> findHashtagByName(@Param("hashtagName") String hashtagName);

    Optional<BlogHashtag> findByBlogHashtagName(String blogHashtagName);

}
