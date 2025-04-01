package application.havenskin.repositories;

import application.havenskin.models.Feedbacks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbacksRepository extends JpaRepository<Feedbacks,String> {
    Feedbacks findByProductIdAndUserId(String productId, String userId);
    List<Feedbacks> findByProductId(String produuctId);
}
