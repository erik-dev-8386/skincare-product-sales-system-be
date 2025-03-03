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

import application.havenskin.dataAccess.CartItemsDTO;
import application.havenskin.dataAccess.OrderDetailDTO;
import application.havenskin.enums.OrderDetailEnums;
import application.havenskin.enums.OrderEnums;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.OrderDetails;
import application.havenskin.models.Orders;
import application.havenskin.models.Products;
import application.havenskin.models.Users;
import application.havenskin.repositories.OrderDetailsRepository;
import application.havenskin.repositories.OrdersRepository;
import application.havenskin.repositories.ProductsRepository;
import application.havenskin.repositories.UserRepository;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    public List<OrderDetails> getAllOrderDetails() {
        return orderDetailsRepository.findAll();
    }

    public OrderDetails addToCart(String email,String productName) {
        Users x = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not Found!"));
        // Kiem tra xem nguoi dung da co don hang nao trong gio chua?
        Orders orders = ordersRepository.findByUserIdAndStatus(x.getUserId(),OrderEnums.UNORDERED.getOrder_status()).orElseGet(
                ()->{
                    Orders newOrder = new Orders();
                    newOrder.setUserId(x.getUserId());
                    newOrder.setStatus(OrderEnums.UNORDERED.getOrder_status());
                    newOrder.setOrderTime(new Date());
                    return ordersRepository.save(newOrder);
                }
                );
        String productId = productsRepository.findByProductName(productName).getProductId();
        Products products = productsRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product Not Found"));

        // san pham co trong gio chua?
        Optional<OrderDetails> existingOrder = orderDetailsRepository.findByOrderIdAndProductId(orders.getOrderId(),productId);
        OrderDetails orderDetails;
        if(existingOrder.isPresent()) {
            orderDetails = existingOrder.get();
            orderDetails.setQuantity(orderDetails.getQuantity() + 1);
        }
        else {
            // tạo giỏ hàng
            orderDetails = new OrderDetails();
            orderDetails.setOrderId(orders.getOrderId());
            orderDetails.setProductId(productId);
            orderDetails.setQuantity(1);
            orderDetails.setDiscountPrice(products.getDiscountPrice());
            orderDetails.setStatus(OrderDetailEnums.ACTIVE.getValue());
        }
        return orderDetailsRepository.save(orderDetails);
    }


    public OrderDetails addToCartQuantity(String email,String productName, int quantity) {
        Users x = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not Found!"));
        // Kiem tra xem nguoi dung da co don hang nao trong gio chua?
        Orders orders = ordersRepository.findByUserIdAndStatus(x.getUserId(),OrderEnums.UNORDERED.getOrder_status()).orElseGet(
                ()->{
                    Orders newOrder = new Orders();
                    newOrder.setUserId(x.getUserId());
                    newOrder.setStatus(OrderEnums.UNORDERED.getOrder_status());
                    newOrder.setOrderTime(new Date());
                    return ordersRepository.save(newOrder);
                }
        );
        String productId = productsRepository.findByProductName(productName).getProductId();
        Products products = productsRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product Not Found"));

        // san pham co trong gio chua?
        Optional<OrderDetails> existingOrder = orderDetailsRepository.findByOrderIdAndProductId(orders.getOrderId(),productId);
        OrderDetails orderDetails;
        if(existingOrder.isPresent()) {
            orderDetails = existingOrder.get();
            orderDetails.setQuantity(orderDetails.getQuantity() + quantity);
        }
        else {
            // tạo giỏ hàng
            orderDetails = new OrderDetails();
            orderDetails.setOrderId(orders.getOrderId());
            orderDetails.setProductId(productId);
            orderDetails.setQuantity(quantity);
            orderDetails.setDiscountPrice(products.getDiscountPrice());
            orderDetails.setStatus(OrderDetailEnums.ACTIVE.getValue());
        }
        return orderDetailsRepository.save(orderDetails);
    }
    // +
    public OrderDetails increaseQuantity(String email,String productName) {
        Users x = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not Found!"));

        // Tim don chua thanh toan cua k/h
        Orders orders = ordersRepository.findByUserIdAndStatus(x.getUserId(),OrderEnums.UNORDERED.getOrder_status()).orElseThrow(() -> new RuntimeException("Order Not Found!"));

        Products products = productsRepository.findByProductName(productName);

        // tìm cart da trên k/h  và sp
        OrderDetails orderDetails = orderDetailsRepository.findByOrderIdAndProductId(orders.getOrderId(),products.getProductId()).orElseThrow(() -> new RuntimeException("OrderDetails Not Found!"));

        if(orders.getStatus() != OrderEnums.UNORDERED.getOrder_status()) {
            throw new RuntimeException("Cannot modify quantity after order is placed");
        }

        orderDetails.setQuantity(orderDetails.getQuantity() + 1);
        return orderDetailsRepository.save(orderDetails);
    }
    // -
    public OrderDetails decreaseQuantity(String email,String productName) {
        Users x = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not Found!"));

        Orders orders = ordersRepository.findByUserIdAndStatus(x.getUserId(),OrderEnums.UNORDERED.getOrder_status()).orElseThrow(() -> new RuntimeException("Order Not Found!"));

        Products products = productsRepository.findByProductName(productName);

        OrderDetails orderDetails = orderDetailsRepository.findByOrderIdAndProductId(orders.getOrderId(), products.getProductId()).orElseThrow(() -> new RuntimeException("OrderDetails Not Found!"));

        if(orders.getStatus() != OrderEnums.UNORDERED.getOrder_status()) {
            throw new RuntimeException("Cannot modify quantity after order is placed");
        }
        // giảm sản phẩm
        if(orderDetails.getQuantity() > 1){
            orderDetails.setQuantity(orderDetails.getQuantity() - 1);
        }
        else {
            orderDetailsRepository.delete(orderDetails);
        }
        return orderDetailsRepository.save(orderDetails);
    }

    public String removeFromCart(String email, String productName) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Not Found!"));

        Orders order = ordersRepository.findByUserIdAndStatus(user.getUserId(), OrderEnums.UNORDERED.getOrder_status())
                .orElseThrow(() -> new RuntimeException("Order Not Found!"));

        Products product = productsRepository.findByProductName(productName);

        OrderDetails orderDetail = orderDetailsRepository.findByOrderIdAndProductId(order.getOrderId(), product.getProductId())
                .orElseThrow(() -> new RuntimeException("OrderDetails Not Found!"));

        if (order.getStatus() != OrderEnums.UNORDERED.getOrder_status()) {
            throw new RuntimeException("Cannot remove product from cart after order is placed");
        }

        orderDetailsRepository.delete(orderDetail);
        return "Successfully removed product from cart";
    }

    public List<CartItemsDTO> getCartItems(String email) {

        Users users = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not Found!"));

        // tim  don hang chua thanh toan cua nguoi dung
        Orders orders = ordersRepository.findByUserIdAndStatus(users.getUserId(), OrderEnums.UNORDERED.getOrder_status()).orElseThrow(() -> new RuntimeException("Order Not Found!"));

        // lay ds cac sp trong gio hang
        List<OrderDetails> orderDetails = orderDetailsRepository.findByOrderId(orders.getOrderId());

        return orderDetails.stream()
                .map(this::convertCartItemsDTO)
                .collect(Collectors.toList());
    }
    // chuyen Order Detail -> cart Items
    private CartItemsDTO convertCartItemsDTO(OrderDetails orderDetails) {
        Products products = productsRepository.findById(orderDetails.getProductId()).orElseThrow(() -> new RuntimeException("Product Not Found!"));

        // tra ve thong tin sp trong gio
        return new CartItemsDTO(
                products.getProductName(),
                orderDetails.getQuantity(),
                products.getDiscountPrice(),
                products.getProductImages()
        );
    }


    public double calculateTotalPrice(String email) {
        Users users = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not Found!"));
        Orders orders = ordersRepository.findByUserIdAndStatus(users.getUserId(), OrderEnums.UNORDERED.getOrder_status()).orElseThrow(() -> new RuntimeException("Order Not Found!"));

        // lấy  các sp
        List<OrderDetails> orderDetails = orderDetailsRepository.findByOrderId(orders.getOrderId());

        double totalPrice = 0;
        for (OrderDetails orderDetail : orderDetails) {
            Products products = productsRepository.findById(orderDetail.getProductId()).orElseThrow(() -> new RuntimeException("Product Not Found!"));
            totalPrice += products.getDiscountPrice();
        }
        return totalPrice;
    }

    public void checkout(String email) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Not Found!"));
        Orders order = ordersRepository.findByUserIdAndStatus(user.getUserId(), OrderEnums.UNORDERED.getOrder_status())
                .orElseThrow(() -> new RuntimeException("Cart Not Found!"));

        // Cập nhật trạng thái đơn hàng
        order.setStatus(OrderEnums.PENDING.getOrder_status());
        ordersRepository.save(order);
    }









//    public OrderDetails updateQuantity(String orderDetailId,String productName, int quantity) {
//        OrderDetails x = orderDetailsRepository.findById(orderDetailId).orElseThrow(()-> new RuntimeException("Order not found"));
//        Orders orders = ordersRepository.findById(x.getOrderId()).orElseThrow(()-> new RuntimeException("Order not found"));
//        if(orders.getStatus() != OrderEnums.UNORDERED.getOrder_status()){
//            throw new RuntimeException("Cannot update product to cart after order is placed");
//        }
//        x.setQuantity(quantity);
//        return orderDetailsRepository.save(x);
//    }

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