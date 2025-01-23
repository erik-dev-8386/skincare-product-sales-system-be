package application.havenskin.repository;

import application.havenskin.BusinessObject.Models.Feedbacks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbacksRepository extends JpaRepository<Feedbacks,String> {
}
