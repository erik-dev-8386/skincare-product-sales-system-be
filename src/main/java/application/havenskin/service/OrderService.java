package application.havenskin.service;

import application.havenskin.BusinessObject.Models.Orders;
import application.havenskin.DTORequest.OrderDTO;
import application.havenskin.mapper.Mapper;
import application.havenskin.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void deleteOrder(String id) {
        ordersRepository.deleteById(id);
    }
    public List<Orders> addListOfOrders(List<Orders> orders) {
        return ordersRepository.saveAll(orders);
    }
    public int ShowQuantityByOrderId(String id) {
        return ordersRepository.findById(id).get().getTotalAmount();
    }
}
