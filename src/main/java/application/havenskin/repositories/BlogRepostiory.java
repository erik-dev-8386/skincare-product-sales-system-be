package application.havenskin.repositories;

import application.havenskin.models.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepostiory extends JpaRepository<Blogs, String> {
}
