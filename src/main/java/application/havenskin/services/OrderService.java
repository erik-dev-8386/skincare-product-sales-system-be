package application.havenskin.services;

import application.havenskin.dataAccess.CartItemDTO;
import application.havenskin.dataAccess.CheckOutResponseDTO;
import application.havenskin.dataAccess.CheckoutRequestDTO;
import application.havenskin.dataAccess.OrderDTO;
import application.havenskin.enums.OrderEnums;
import application.havenskin.enums.ProductEnums;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.OrderDetails;
import application.havenskin.models.Orders;
import application.havenskin.models.Products;
import application.havenskin.models.Users;
import application.havenskin.repositories.OrderDetailsRepository;
import application.havenskin.repositories.OrdersRepository;
import application.havenskin.repositories.ProductsRepository;
import application.havenskin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private Mapper mapper;
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

//    public void checkout(CheckoutRequestDTO checkoutRequestDTO) {
//        Users x = userRepository.findByEmail(checkoutRequestDTO.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
//
//        // tao đơn hàng mới
//        Orders order = new Orders();
//        order.setUserId(x.getUserId());
//        order.setStatus(OrderEnums.PENDING.getOrder_status());
//        order.setOrderTime(new Date());
////        ordersRepository.save(order);
//
//        double totalOrderPrice = 0;
//        for (CartItemDTO cartItemDTO : checkoutRequestDTO.getCartItemDTOS()) {
//            Products products = productsRepository.findByProductName(cartItemDTO.getProductName());
//            if(products == null) {
//                throw new RuntimeException(cartItemDTO.getProductName() + " not found");
//            }
//            if(products.getQuantity() < cartItemDTO.getQuantity()) {
//                throw  new RuntimeException(cartItemDTO.getProductName()+" not enough");
//            }
//
//            double itemTotalPrice = products.getDiscountPrice() * cartItemDTO.getQuantity();
//            totalOrderPrice += itemTotalPrice;
//
//            OrderDetails orderDetails = new OrderDetails();
//            orderDetails.setOrderId(order.getOrderId());
//            orderDetails.setProductId(products.getProductId());
//            orderDetails.setQuantity(cartItemDTO.getQuantity());
//            orderDetails.setDiscountPrice(products.getDiscountPrice());
//            orderDetails.setStatus(OrderEnums.PENDING.getOrder_status());
//
//            orderDetailsRepository.save(orderDetails);
//
//            // cập nhật so luong ton kho
//            products.setQuantity(products.getQuantity() - cartItemDTO.getQuantity());
//            if(products.getQuantity() <= 0){
//                products.setStatus(ProductEnums.OUT_OF_STOCK.getValue());
//            }
//            productsRepository.save(products);
//
//            order.setTotalAmount(totalOrderPrice);
//            ordersRepository.save(order);
//
//            order.setStatus(OrderEnums.PENDING.getOrder_status());
//            ordersRepository.save(order);
//        }
//    }

public CheckOutResponseDTO checkout(CheckoutRequestDTO checkoutRequestDTO) {
    Users x = userRepository.findByEmail(checkoutRequestDTO.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

    // tao đơn hàng mới
    Orders order = new Orders();
    order.setUserId(x.getUserId());
    order.setStatus(OrderEnums.PENDING.getOrder_status());
    order.setOrderTime(new Date());
//        ordersRepository.save(order);

    double totalOrderPrice = 0;
    for (CartItemDTO cartItemDTO : checkoutRequestDTO.getCartItemDTO()) {
        Products products = productsRepository.findByProductName(cartItemDTO.getProductName());
        if (products == null) {
            throw new RuntimeException(cartItemDTO.getProductName() + " not found");
        }
        if (products.getQuantity() < cartItemDTO.getQuantity()) {
            throw new RuntimeException(cartItemDTO.getProductName() + " not enough");
        }

        double itemTotalPrice = products.getDiscountPrice() * cartItemDTO.getQuantity();
        totalOrderPrice += itemTotalPrice;

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(order.getOrderId());
        orderDetails.setProductId(products.getProductId());
        orderDetails.setQuantity(cartItemDTO.getQuantity());
        orderDetails.setDiscountPrice(products.getDiscountPrice());
        orderDetails.setStatus(OrderEnums.PENDING.getOrder_status());

        orderDetailsRepository.save(orderDetails);

        // cập nhật so luong ton kho
        products.setQuantity(products.getQuantity() - cartItemDTO.getQuantity());
        if (products.getQuantity() <= 0) {
            products.setStatus(ProductEnums.OUT_OF_STOCK.getValue());
        }
        productsRepository.save(products);
    }
        order.setTotalAmount(totalOrderPrice);
        ordersRepository.save(order);

        order.setStatus(OrderEnums.PENDING.getOrder_status());
        ordersRepository.save(order);

        CheckOutResponseDTO response = new CheckOutResponseDTO();
        response.setTotal(totalOrderPrice);
        response.setCartItems(checkoutRequestDTO.getCartItemDTO().stream()
                .map(detail ->{
                    CartItemDTO temp = new CartItemDTO();
                    temp.setProductName(detail.getProductName());
                    temp.setQuantity(detail.getQuantity());
                    temp.setPrice(detail.getPrice());
                    return temp;
                }).collect(Collectors.toList()));
        return response;
    }


    public Orders findCartByUserId(String email){
        String userId = userRepository.findByEmail(email).get().getUserId();
        if(userId == null) {
            throw new RuntimeException("User not found");
        }
        else{
            Optional<Orders> orders = ordersRepository.findByUserIdAndStatus(userId, OrderEnums.PENDING.getOrder_status());
            if(orders.isPresent()) {
                return orders.get();
            }
            else{
                throw new RuntimeException("Order not found");
            }
        }
    }
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

//    public int ShowQuantityByOrderId(String id) {
//        return ordersRepository.findById(id).get().getTotalAmount();
//    }

    public boolean updateOrderStatus(String orderId, byte newStatusByte) {
        Optional<Orders> orderOpt = ordersRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Orders order = orderOpt.get();
            OrderEnums currentStatus = OrderEnums.fromOrderStatus(order.getStatus());
            OrderEnums newStatus = OrderEnums.fromOrderStatus(newStatusByte);

            // Kiểm tra trạng thái hợp lệ
            if (!isValidStatusTransition(currentStatus, newStatus)) {
                return false; // Tránh cập nhật trạng thái sai logic
            }

            order.setStatus(newStatus.getOrder_status());
            ordersRepository.save(order);
            return true;
        }
        return false;
    }

    private boolean isValidStatusTransition(OrderEnums currentStatus, OrderEnums newStatus) {
        Map<OrderEnums, List<OrderEnums>> validTransitions = new HashMap<>();

        validTransitions.put(OrderEnums.UNORDERED, List.of(OrderEnums.PENDING, OrderEnums.CANCELLED));
        validTransitions.put(OrderEnums.PENDING, List.of(OrderEnums.PROCESSING, OrderEnums.CANCELLED));
        validTransitions.put(OrderEnums.PROCESSING, List.of(OrderEnums.SHIPPING, OrderEnums.CANCELLED));
        validTransitions.put(OrderEnums.SHIPPING, List.of(OrderEnums.DELIVERED, OrderEnums.RETURNED));
        validTransitions.put(OrderEnums.DELIVERED, List.of());
        validTransitions.put(OrderEnums.CANCELLED, List.of());
        validTransitions.put(OrderEnums.RETURNED, List.of());

        return validTransitions.getOrDefault(currentStatus, List.of()).contains(newStatus);
    }


}
