package application.havenskin.services;

import application.havenskin.dataAccess.*;
import application.havenskin.enums.OrderDetailEnums;
import application.havenskin.enums.OrderEnums;
import application.havenskin.enums.ProductEnums;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.*;
import application.havenskin.repositories.*;
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
    @Autowired
    private CoinWalletsRepository coinWalletsRepository;
//
//    public void checkout(CheckoutRequestDTO checkoutRequestDTO) {
//        Users x = userRepository.findByEmail(checkoutRequestDTO.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
//
//        // tao đơn hàng mới
//        Orders order = new Orders();
//        order.setUserId(x.getUserId());
//        order.setStatus(OrderEnums.PENDING.getOrder_status());
//        order.setOrderTime(new Date());

    /// /        ordersRepository.save(order);
//
//        double totalOrderPrice = 0;
//        for (CartItemDTO cartItemDTO : checkoutRequestDTO.getCartItemDTO()) {
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
//        if (checkoutRequestDTO.getCartItemDTO() == null || checkoutRequestDTO.getCartItemDTO().isEmpty()) {
//            throw new RuntimeException("Cart is empty. Cannot create order.");
//        }
        Users x = userRepository.findByEmail(checkoutRequestDTO.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        // tao đơn hàng mới
        Orders order = new Orders();
        order.setUserId(x.getUserId());
        order.setStatus(OrderEnums.PENDING.getOrder_status());
        order.setOrderTime(new Date());
        ordersRepository.save(order);
//    Products productCart = null;
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
 //           orderDetails.setOrderId(order.getOrderId());
            orderDetails.setOrderId(order.getOrderId());
            orderDetails.setProductId(products.getProductId());
            orderDetails.setQuantity(cartItemDTO.getQuantity());
            orderDetails.setDiscountPrice(products.getDiscountPrice());
            orderDetails.setStatus(OrderEnums.PENDING.getOrder_status());

            orderDetailsRepository.save(orderDetails);

            // cập nhật so luong ton kho
            products.setQuantity(products.getQuantity() - cartItemDTO.getQuantity());
            products.setSoldQuantity(products.getSoldQuantity() + cartItemDTO.getQuantity());
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
        response.setOrderId(order.getOrderId());
//        response.setCartItems(checkoutRequestDTO.getCartItemDTO().stream()
//                .map(detail ->{
//                    CartItemDTO temp = new CartItemDTO();
//                    temp.setProductName(detail.getProductName());
//                    temp.setQuantity(detail.getQuantity());
//                    temp.setPrice(detail.getPrice());
//                    return temp;
//                }).collect(Collectors.toList()));
        response.setCartItems(checkoutRequestDTO.getCartItemDTO().stream().map(detail -> {
            Products productsCartItem = productsRepository.findByProductName(detail.getProductName());
            CartItemResponseDTO temp = new CartItemResponseDTO();
            temp.setProductName(detail.getProductName());
            temp.setQuantity(detail.getQuantity());
//            temp.setPrice(detail.getPrice());
            temp.setDiscountPrice(productsCartItem.getDiscountPrice());
//            temp.setImageUrl(productCart.getProductImages());
            if (productsCartItem != null && productsCartItem.getProductImages() != null && !productsCartItem.getProductImages().isEmpty()) {
                // chọn ảnh đầu tiên cho fe
                String image = productsCartItem.getProductImages().get(0).getImageURL();
                temp.setImageUrl(image);
            } else {
                temp.setImageUrl(null);
            }

            return temp;
        }).collect(Collectors.toList()));
        return response;
    }

    public void cancelOrder(String email, String orderId) {
//        String userId = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"))
//                .getUserId();

        String userId = userRepository.findByEmail(email).get().getUserId();
        Orders orders = ordersRepository.findByOrderIdAndUserId(orderId, userId).orElseThrow(() -> new RuntimeException("Order not found"));
//        Orders orders = ordersRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        System.out.println("Order:" + orders.toString());
        if(orders.getStatus() != OrderEnums.PENDING.getOrder_status()) {
            throw new RuntimeException("Chỉ có thể hủy đơn hàng ở trạng thái PENDING");
        }

        List<OrderDetails> orderDetails = orderDetailsRepository.findByOrderId(orderId);

        for (OrderDetails orderDetail : orderDetails) {
            Products products = productsRepository.findById(orderDetail.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

            products.setQuantity(products.getQuantity() + orderDetail.getQuantity());
            products.setSoldQuantity(products.getSoldQuantity() - orderDetail.getQuantity());

            if(products.getQuantity() <= 0) {
                products.setStatus(ProductEnums.OUT_OF_STOCK.getValue());
            }
            productsRepository.save(products);
        }
        orders.setStatus(OrderEnums.CANCELLED.getOrder_status());
        ordersRepository.save(orders);
    }


    public Orders findCartByUserId(String email) {
        String userId = userRepository.findByEmail(email).get().getUserId();
        if (userId == null) {
            throw new RuntimeException("User not found");
        } else {
            Optional<Orders> orders = ordersRepository.findByUserIdAndStatus(userId, OrderEnums.PENDING.getOrder_status());
            if (orders.isPresent()) {
                return orders.get();
            } else {
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
        Orders x = ordersRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
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

            if (currentStatus == OrderEnums.UNORDERED && newStatus != OrderEnums.UNORDERED) {
                lockOrderDetails(order.getOrderId());
            }

            if (newStatus == OrderEnums.DELIVERED) {
                Optional<CoinWallets> coinWalletOpt = coinWalletsRepository.findByUserId(order.getUserId());
                if (coinWalletOpt.isPresent()) {
                    CoinWallets coinWallet = coinWalletOpt.get();
                    double rewardAmount = order.getTotalAmount() * 0.01; // 1% thưởng
                    coinWallet.setBalance(coinWallet.getBalance() + rewardAmount);
                    coinWalletsRepository.save(coinWallet); // Lưu số dư mới
                }
            }

            order.setStatus(newStatus.getOrder_status());
            ordersRepository.save(order);
            return true;
        }
        return false;
    }

    // Hàm khóa OrderDetails
    private void lockOrderDetails(String orderId) {
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findByOrderId(orderId);
        for (OrderDetails detail : orderDetailsList) {
            detail.setStatus(OrderDetailEnums.INACTIVE.getValue());
        }
        orderDetailsRepository.saveAll(orderDetailsList);
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

    public List<HistoryOrderDTO> getHistoryOrder(String email) {
        Optional<Users> userOpt = userRepository.findByEmail(email);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        Users user = userOpt.get();
        String userId = user.getUserId();
        List<Orders> orders = ordersRepository.findByUserId(userId);

        List<HistoryOrderDTO> historyOrders = orders.stream().map(x->{
            HistoryOrderDTO historyOrder = new HistoryOrderDTO();
            historyOrder.setOrderId(x.getOrderId());
            historyOrder.setOrderTime(x.getOrderTime());
            historyOrder.setTotalAmount(x.getTotalAmount());
            historyOrder.setStatus(x.getStatus());
            List<OrderDetails> orderDetails = orderDetailsRepository.findByOrderId(x.getOrderId());
            historyOrder.setQuantity(orderDetails.size());
            List<ProductDetailsDTO> productDetails = orderDetails.stream().map(detail ->{
                ProductDetailsDTO productDetail = new ProductDetailsDTO();
                Products products = productsRepository.findById(detail.getProductId()).get();
                if(products == null) {
                    throw new RuntimeException("Product not found");
                }
                productDetail.setProductName(products.getProductName());
                productDetail.setQuantity(detail.getQuantity());
                productDetail.setDiscountPrice(detail.getDiscountPrice());
                if(products.getProductImages() != null && !products.getProductImages().isEmpty()) {
                    productDetail.setImageUrl(products.getProductImages().get(0).getImageURL());
                }else{
                    productDetail.setImageUrl(null);
                }

                return productDetail;
            }).collect(Collectors.toList());
//            List<String> productName = orderDetails.stream().map(details->details.getProducts().getProductName()).collect(Collectors.toList());
//            historyOrder.setProductName(productName);
//            historyOrder.setTotalAmount(x.getTotalAmount());
//            historyOrder.setQuantity(orderDetails.getQuantity());
            historyOrder.setProductName(productDetails);
            return historyOrder;
        }).collect(Collectors.toList());
        return historyOrders;
    }

    public HistoryOrderDTO getOrderById(String email, String orderId){
        Optional<Users> userOpt = userRepository.findByEmail(email);
        if(!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        Users user = userOpt.get();
        String userId = user.getUserId();

        Optional<Orders> orderOpt = ordersRepository.findByOrderIdAndUserId(orderId, userId);
        if(!orderOpt.isPresent()) {
            throw new RuntimeException("Order not found");
        }
        Orders order = orderOpt.get();

        HistoryOrderDTO historyOrder = new HistoryOrderDTO();
        historyOrder.setOrderId(order.getOrderId());
        historyOrder.setOrderTime(order.getOrderTime());
        historyOrder.setTotalAmount(order.getTotalAmount());
        historyOrder.setStatus(order.getStatus());

        List<OrderDetails> orderDetails = orderDetailsRepository.findByOrderId(order.getOrderId());
        historyOrder.setQuantity(orderDetails.size());

        List<ProductDetailsDTO> productDetails = orderDetails.stream().map(detail->{
            ProductDetailsDTO productDetail = new ProductDetailsDTO();
            Products products = productsRepository.findById(detail.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

            productDetail.setProductName(products.getProductName());
            productDetail.setQuantity(detail.getQuantity());
            productDetail.setDiscountPrice(detail.getDiscountPrice());

            if(products.getProductImages() != null && !products.getProductImages().isEmpty()) {
                productDetail.setImageUrl(products.getProductImages().get(0).getImageURL());
            }
            else{
                productDetail.setImageUrl(null);
            }
            return productDetail;
        }).collect(Collectors.toList());
        historyOrder.setProductName(productDetails);
        return historyOrder;
    }

}
