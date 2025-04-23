package application.havenskin.repositories;

import application.havenskin.models.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blogs, String> {
    @Query("SELECT b FROM Blogs b JOIN b.hashtags h WHERE LOWER(TRIM(h.blogHashtagName)) = LOWER(TRIM(:hashtagName))")
    List<Blogs> findByHashtagName(@Param("hashtagName") String hashtagName);


//    @Query("SELECT b FROM Blogs b WHERE LOWER(b.blogTitle) = LOWER(:title)")
//    Blogs  findByTitle(@Param("title") String title);

    Blogs findByBlogTitleIgnoreCase(String title);




    List<Blogs> findByBlogContent(String blogContent);

    List<Blogs> findByBlogCategory_BlogCategoryName(String blogCategoryBlogCategoryName);
}
