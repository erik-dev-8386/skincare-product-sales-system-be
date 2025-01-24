package application.havenskin.Services;

import application.havenskin.Models.Orders;
import application.havenskin.Repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public Orders getOrderById(String orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public Orders saveOrder(Orders orders) {
        return orderRepository.save(orders);
    }

    public void deleteOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }
}
