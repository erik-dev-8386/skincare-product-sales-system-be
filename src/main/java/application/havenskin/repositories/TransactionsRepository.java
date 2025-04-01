package application.havenskin.repositories;

import application.havenskin.models.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, String> {
    Transactions findByOrderId(String orderId);

    boolean existsByOrderId(String orderId);


    boolean existsByTransactionCode(String transactionCode);
//    Transactions findByTransactionCode(String transactionCode);

    Optional<Transactions> findByTransactionCode(String transactionCode);

}
