package application.havenskin.services;

import application.havenskin.dataAccess.OrderDTO;
import application.havenskin.enums.OrderEnums;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.Orders;
import application.havenskin.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private Mapper mapper;
    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }
    public Orders getOrderById(String id) {
        return ordersRepository.getById(id);
    }
    public Orders createOrder(Orders order) {
        return ordersRepository.save(order);
    }
    public Orders updateOrder(String id, OrderDTO order) {
        Orders x = ordersRepository.findById(id).orElseThrow(()-> new RuntimeException("Order not found"));
        mapper.updateOrders(x, order);
        return ordersRepository.save(x);
    }
    public Orders deleteOrder(String id) {
        //ordersRepository.deleteById(id);
        Optional<Orders> x = ordersRepository.findById(id);
        if (x.isPresent()) {
            Orders order = x.get();
            order.setStatus(OrderEnums.CANCELLED.getOrder_status());
            return ordersRepository.save(order);
        }
        return null;
    }
    public List<Orders> addListOfOrders(List<Orders> orders) {
        return ordersRepository.saveAll(orders);
    }
    public int ShowQuantityByOrderId(String id) {
        return ordersRepository.findById(id).get().getTotalAmount();
    }
}
