package application.havenskin.Controllers;

import application.havenskin.Models.Orders;
import application.havenskin.Services.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Orders> getAllOrders() {
        return transactionService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public Orders getOrderById(@PathVariable String orderId) {
        return transactionService.getOrderById(orderId);
    }

    @PostMapping
    public Orders createOrder(@RequestBody Orders orders) {
        return transactionService.saveOrder(orders);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable String orderId) {
        transactionService.deleteOrder(orderId);
    }
}
