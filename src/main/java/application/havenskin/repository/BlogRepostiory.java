package application.havenskin.repository;

import application.havenskin.BusinessObject.Models.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepostiory extends JpaRepository<Blogs, String> {
}
