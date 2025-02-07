package application.havenskin.Repositories;

import application.havenskin.Models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Orders, String> {
}
