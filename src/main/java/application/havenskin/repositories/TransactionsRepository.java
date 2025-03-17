package application.havenskin.repositories;

import application.havenskin.models.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, String> {
    Transactions findByOrderId(String orderId);
}
