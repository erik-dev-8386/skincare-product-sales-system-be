package application.havenskin.repository;

import application.havenskin.BusinessObject.Models.ResultTests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultTestsRepository  extends JpaRepository<ResultTests, String> {
}
