package application.havenskin.service;

import application.havenskin.BusinessObject.Models.Orders;
import application.havenskin.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrdersRepository ordersRepository;
    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }
    public Orders getOrderById(String id) {
        return ordersRepository.getById(id);
    }
    public Orders createOrder(Orders order) {
        return ordersRepository.save(order);
    }
    public Orders updateOrder(String id,Orders order) {
        if(getOrderById(id) == null) {
            throw new RuntimeException("Order not found");
        }
        return ordersRepository.save(order);
    }
    public void deleteOrder(String id) {
        ordersRepository.deleteById(id);
    }
}
