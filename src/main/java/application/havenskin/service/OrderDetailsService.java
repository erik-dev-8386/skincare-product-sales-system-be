package application.havenskin.service;

import application.havenskin.BusinessObject.Models.OrderDetails;
import application.havenskin.DTORequest.OrderDetailDTO;
import application.havenskin.mapper.Mapper;
import application.havenskin.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsService {
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private Mapper mapper;
    public List<OrderDetails> getAllOrderDetails() {
        return orderDetailsRepository.findAll();
    }
    public OrderDetails getOrderDetailsByID(String orderId) {
        if (!orderDetailsRepository.existsById(orderId)) {
            throw new RuntimeException("Order not found");
        }
        return orderDetailsRepository.findById(orderId).get();
    }
    public OrderDetails addOrderDetails(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }
    public OrderDetails updateOrderDetails(String id, OrderDetailDTO orderDetails) {
        OrderDetails x = orderDetailsRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        mapper.updateOrderDetails(x, orderDetails);
    return orderDetailsRepository.save(x);
    }
    public void deleteOrderDetails(String id) {
        if (!orderDetailsRepository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }
        orderDetailsRepository.deleteById(id);
    }
    public List<OrderDetails> addListOfOrderDetails(List<OrderDetails> orderDetails) {
        return orderDetailsRepository.saveAll(orderDetails);
    }
}
