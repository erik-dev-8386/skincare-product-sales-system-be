package application.havenskin.services;

import application.havenskin.dataAccess.OrderDetailDTO;
import application.havenskin.enums.OrderDetailEnums;
import application.havenskin.enums.OrderEnums;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.OrderDetails;
import application.havenskin.models.Orders;
import application.havenskin.models.Orders;
import application.havenskin.models.Products;
import application.havenskin.models.Users;
import application.havenskin.repositories.OrderDetailsRepository;
import application.havenskin.repositories.OrdersRepository;
import application.havenskin.repositories.OrdersRepository;
import application.havenskin.repositories.ProductsRepository;
import application.havenskin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderDetailsService {
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private Mapper mapper;

    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderService orderService;
    public List<OrderDetails> getAllOrderDetails() {
        return orderDetailsRepository.findAll();
    }

    public OrderDetails getOrderDetailsByID(String orderDetailId) {
        return orderDetailsRepository.findById(orderDetailId)
                .orElseThrow(() -> new RuntimeException("OrderDetail not found"));
    }

    public OrderDetails addOrderDetails(OrderDetails orderDetails) {
        // Lấy order từ order_id
        Orders order = ordersRepository.findById(orderDetails.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Kiểm tra trạng thái của order
        if (order.getStatus() != OrderEnums.UNORDERED.getOrder_status()) {
            throw new RuntimeException("Cannot modify OrderDetails after order is placed");
        }

        return orderDetailsRepository.save(orderDetails);
    }

    public OrderDetails updateOrderDetails(String id, OrderDetailDTO orderDetails) {
        OrderDetails existingDetail = orderDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderDetail not found"));

        // Kiểm tra trạng thái của order
        Orders order = ordersRepository.findById(existingDetail.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (order.getStatus() != OrderEnums.UNORDERED.getOrder_status()) {
            throw new RuntimeException("Cannot modify OrderDetails after order is placed");
        }

        // Cập nhật order details
        mapper.updateOrderDetails(existingDetail, orderDetails);
        return orderDetailsRepository.save(existingDetail);
    }



    public OrderDetails deleteOrderDetails(String id) {
//        if (!orderDetailsRepository.existsById(id)) {
//            throw new RuntimeException("Order not found");
//        }
//        orderDetailsRepository.deleteById(id);
        Optional<OrderDetails> orderDetailsOption = orderDetailsRepository.findById(id);
        if (orderDetailsOption.isPresent()) {
            OrderDetails detail = orderDetailsOption.get();

            // Kiểm tra trạng thái của order
            Orders order = ordersRepository.findById(detail.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            if (order.getStatus() != OrderEnums.UNORDERED.getOrder_status()) {
                throw new RuntimeException("Cannot delete OrderDetails after order is placed");
            }

            detail.setStatus(OrderDetailEnums.INACTIVE.getValue());
            return orderDetailsRepository.save(detail);
        }
        return null;
    }

    public List<OrderDetails> addListOfOrderDetails(List<OrderDetails> orderDetailsList) {
        for (OrderDetails detail : orderDetailsList) {
            Orders order = ordersRepository.findById(detail.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            if (order.getStatus() != OrderEnums.UNORDERED.getOrder_status()) {
                throw new RuntimeException("Cannot modify OrderDetails after order is placed");
            }
        }
        return orderDetailsRepository.saveAll(orderDetailsList);
    }
}
