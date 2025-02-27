//package application.havenskin.services;
//
//import application.havenskin.dataAccess.OrderDetailDTO;
//import application.havenskin.enums.OrderDetailEnums;
//import application.havenskin.enums.OrderEnums;
//import application.havenskin.mapper.Mapper;
//import application.havenskin.models.OrderDetails;
//import application.havenskin.models.Orders;
//import application.havenskin.repositories.OrderDetailsRepository;
//import application.havenskin.repositories.OrdersRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class OrderDetailsService {
//    @Autowired
//    private OrderDetailsRepository orderDetailsRepository;
//    @Autowired
//    private OrdersRepository ordersRepository;
//    @Autowired
//    private Mapper mapper;
//    public List<OrderDetails> getAllOrderDetails() {
//        return orderDetailsRepository.findAll();
//    }
//    public OrderDetails getOrderDetailsByID(String orderId) {
//        if (!orderDetailsRepository.existsById(orderId)) {
//            throw new RuntimeException("Order not found");
//        }
//        return orderDetailsRepository.findById(orderId).get();
//    }
//    public OrderDetails addOrderDetails(OrderDetails orderDetails) {
//        return orderDetailsRepository.save(orderDetails);
//    }
////    public OrderDetails updateOrderDetails(String id, OrderDetailDTO orderDetails) {
////        OrderDetails x = orderDetailsRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
////        mapper.updateOrderDetails(x, orderDetails);
////    return orderDetailsRepository.save(x);
////    }
//public OrderDetails updateOrderDetails(String id, OrderDetailDTO orderDetails) {
//    OrderDetails existingDetail = orderDetailsRepository.findById(id)
//            .orElseThrow(() -> new RuntimeException("OrderDetail not found"));
//
//    // Kiểm tra trạng thái của order
//    Orders order = ordersRepository.findById(existingDetail.getOrderId())
//            .orElseThrow(() -> new RuntimeException("Order not found"));
//    if (order.getStatus() != OrderEnums.UNORDERED.getOrder_status()) {
//        throw new RuntimeException("Cannot modify OrderDetails after order is placed");
//    }
//
//    // Cập nhật order details
//    mapper.updateOrderDetails(existingDetail, orderDetails);
//    return orderDetailsRepository.save(existingDetail);
//}
////    public OrderDetails deleteOrderDetails(String id) {
//////        if (!orderDetailsRepository.existsById(id)) {
//////            throw new RuntimeException("Order not found");
//////        }
//////        orderDetailsRepository.deleteById(id);
////        Optional<OrderDetails> orderDetailsOption = orderDetailsRepository.findById(id);
////        if(orderDetailsOption.isPresent()) {
////            OrderDetails x = orderDetailsOption.get();
////            x.setStatus(OrderDetailEnums.INACTIVE.getValue());
////            return orderDetailsRepository.save(x);
////        }
////        return null;
////    }
//public OrderDetails deleteOrderDetails(String id) {
//    Optional<OrderDetails> orderDetailsOption = orderDetailsRepository.findById(id);
//    if (orderDetailsOption.isPresent()) {
//        OrderDetails detail = orderDetailsOption.get();
//
//        // Kiểm tra trạng thái của order
//        Orders order = ordersRepository.findById(detail.getOrderId())
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//        if (order.getStatus() != OrderEnums.UNORDERED.getOrder_status()) {
//            throw new RuntimeException("Cannot delete OrderDetails after order is placed");
//        }
//
//        detail.setStatus(OrderDetailEnums.INACTIVE.getValue());
//        return orderDetailsRepository.save(detail);
//    }
//    return null;
//}
//    public List<OrderDetails> addListOfOrderDetails(List<OrderDetails> orderDetails) {
//        return orderDetailsRepository.saveAll(orderDetails);
//    }
////    public OrderDetails updateProductQuantityInCart(String orderId,String productId, int newquantity) {
////        Orders x = ordersRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));
////        if(x.getStatus() != OrderEnums.CANCELLED.getOrder_status()){
////            throw new RuntimeException("Không thể thay đổi số lượng các sản phẩm");
////        }
////        OrderDetails orderDetails = orderDetailsRepository.
////
////    }
//}
package application.havenskin.services;

import application.havenskin.dataAccess.OrderDetailDTO;
import application.havenskin.enums.OrderDetailEnums;
import application.havenskin.enums.OrderEnums;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.OrderDetails;
import application.havenskin.models.Orders;
import application.havenskin.models.Products;
import application.havenskin.repositories.OrderDetailsRepository;
import application.havenskin.repositories.OrdersRepository;
import application.havenskin.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<OrderDetails> getAllOrderDetails() {
        return orderDetailsRepository.findAll();
    }

//    public OrderDetails addToCart(String orderId,String productName, int quantity) {
//        Orders x = ordersRepository.findById(orderId).orElseThrow(()-> new RuntimeException("Order not found"));
//        if(x.getStatus() != OrderEnums.UNORDERED.getOrder_status()){
//            throw new RuntimeException("Cannot add product to cart after order is placed");
//        }
//        String productId = productsRepository.findProductIDByName(productName);
//        Products products = productsRepository.findById(productId).orElseThrow(()-> new RuntimeException("Product not found"));
//        Optional<OrderDetails>existing = orderDetailsRepository.findByOrderIdAndCustomerId(orderId, productId);
//        OrderDetails orderDetail;
//        if(existing.isPresent()){
//            orderDetail = existing.get();
//            orderDetail.setQuantity(orderDetail.getQuantity() + quantity);
//        }
//        else{
//            orderDetail = new OrderDetails();
//            orderDetail.setOrderId(orderId);
//            orderDetail.setProductId(productId);
//            orderDetail.setQuantity(quantity);
//            orderDetail.setDiscountPrice(products.getDiscountPrice());
//            orderDetail.setStatus(OrderDetailEnums.ACTIVE.getValue());
//        }
//        return orderDetailsRepository.save(orderDetail);
//    }
    public OrderDetails updateQuantity(String orderDetailId,String productName, int quantity) {
        OrderDetails x = orderDetailsRepository.findById(orderDetailId).orElseThrow(()-> new RuntimeException("Order not found"));
        Orders orders = ordersRepository.findById(x.getOrderId()).orElseThrow(()-> new RuntimeException("Order not found"));
        if(orders.getStatus() != OrderEnums.UNORDERED.getOrder_status()){
            throw new RuntimeException("Cannot update product to cart after order is placed");
        }
        x.setQuantity(quantity);
        return orderDetailsRepository.save(x);
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