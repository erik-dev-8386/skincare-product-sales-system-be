package application.havenskin.Services;

import application.havenskin.Models.Orders;
import application.havenskin.Repositories.OrderRepository;
import application.havenskin.Repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Orders> getAllOrders() {
        return transactionRepository.findAll();
    }

    public Orders getOrderById(String orderId) {
        return transactionRepository.findById(orderId).orElse(null);
    }

    public Orders saveOrder(Orders orders) {
        return transactionRepository.save(orders);
    }

    public void deleteOrder(String orderId) {
        transactionRepository.deleteById(orderId);
    }

}
