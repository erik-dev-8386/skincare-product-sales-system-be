package application.havenskin.services;

import application.havenskin.dataAccess.*;
import application.havenskin.enums.OrderDetailEnums;
import application.havenskin.enums.OrderEnums;
import application.havenskin.enums.ProductEnums;
import application.havenskin.enums.TransactionsEnums;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.*;
import application.havenskin.repositories.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
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
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private EmailService emailService;

    @Autowired
    private TransactionsRepository transactionRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;

    public CheckOutResponseDTO checkout(CheckoutRequestDTO checkoutRequestDTO) {

        Users users = null;
        if (!checkoutRequestDTO.isGuest()) {
            users = userRepository.findByEmail(checkoutRequestDTO.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        }
        // tao đơn hàng mới
        Orders order = new Orders();
        if (users != null) {
            order.setUserId(users.getUserId());
            order.setAddress(users.getAddress());
            //order.setCustomerName(users.getFirstName() +" "+ users.getLastName());
            order.setCustomerFirstName(users.getFirstName());
            order.setCustomerLastName(users.getLastName());
            order.setCustomerPhone(users.getPhone());
            order.setStatus(OrderEnums.PENDING.getOrder_status());
            order.setOrderTime(new Date());
            ordersRepository.save(order);
        } else {
            order.setUserId(null);
        }
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

            Transactions transactions = new Transactions();
            transactions.setOrderId(order.getOrderId());
            transactions.setTransactionTime(LocalDateTime.now());
            transactionsRepository.save(transactions);
            return temp;
        }).collect(Collectors.toList()));
        if (checkoutRequestDTO.getEmail() != null && !checkoutRequestDTO.getEmail().isEmpty()) {
            sendOrderConfirmationEmail(checkoutRequestDTO.getEmail(), order.getOrderId(), order.getTotalAmount(), order.getOrderTime(), (List<CartItemResponseDTO>) response.getCartItems());
        }
        else {
            log.warn("Không có email để gửi xác nhận đơn hàng.");
        }
//    sendOrderConfirmationEmail(checkoutRequestDTO.getEmail(), order.getOrderId(), order.getTotalAmount(), order.getOrderTime(), (List<CartItemResponseDTO>) response.getCartItems());
        return response;
    }

    private void sendOrderConfirmationEmail(String to, String orderId, double totalAmount, Date orderDate, List<CartItemResponseDTO> cartItems) {
        // Tạo tiêu đề email
        String subject = "Haven Skin - Xác nhận đơn hàng #" + orderId;

        // Tạo danh sách sản phẩm
        StringBuilder productDetails = new StringBuilder();
        for (CartItemResponseDTO item : cartItems) {
            Products product = productsRepository.findByProductName(item.getProductName());
            if (product != null) {
                productDetails.append(String.format("%-30s %-10s %-10s%n",
                        item.getProductName(),
                        "             " + item.getQuantity(),
                        "                                                   " + product.getDiscountPrice()));
            }
        }

        // Tạo nội dung email
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("=============================================\n");
        emailContent.append("           HAVEN SKIN - XÁC NHẬN ĐƠN HÀNG          \n");
        emailContent.append("=============================================\n\n");
        emailContent.append("Cảm ơn bạn đã đặt hàng tại Haven Skin! Đơn hàng của bạn đã được xác nhận.\n\n");
        emailContent.append("Chi tiết đơn hàng:\n");
        emailContent.append("Mã đơn hàng: ").append(orderId).append("\n");
        emailContent.append("Ngày đặt hàng: ").append(orderDate).append("\n\n");
        emailContent.append("Danh sách sản phẩm:\n");
        emailContent.append(String.format("%-50s %-50s %-30s%n", "Tên sản phẩm", "Số lượng", "Giá"));
        emailContent.append("-----------------------------------------------------------------------------------------------------------------------------------\n");
        emailContent.append(productDetails.toString()).append("\n");
        emailContent.append("Tổng tiền: ").append(totalAmount).append("\n\n");
        emailContent.append("Nếu bạn có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi:\n");
        emailContent.append("Số điện thoại: 0966340303\n");
        emailContent.append("Email: havenskin032025@gmail.com\n\n");
        emailContent.append("Trân trọng,\n");
        emailContent.append("Đội ngũ hỗ trợ Haven Skin\n");
//        emailContent.append("=============================================\n");

        // Gửi email
        emailService.sendEmail(to, subject, emailContent.toString());
    }

    public List<Orders> getOrdersList(String orderId) {
        return ordersRepository.findByOrderIdContaining(orderId);
    }

    public void cancelOrder(String email, String orderId) {
//        String userId = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"))
//                .getUserId();

        String userId = userRepository.findByEmail(email).get().getUserId();
        Orders orders = ordersRepository.findByOrderIdAndUserId(orderId, userId).orElseThrow(() -> new RuntimeException("Order not found"));
//        Orders orders = ordersRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        System.out.println("Order:" + orders.toString());
        if (orders.getStatus() != OrderEnums.PENDING.getOrder_status()) {
            throw new RuntimeException("Chỉ có thể hủy đơn hàng ở trạng thái PENDING");
        }

        List<OrderDetails> orderDetails = orderDetailsRepository.findByOrderId(orderId);

        for (OrderDetails orderDetail : orderDetails) {
            Products products = productsRepository.findById(orderDetail.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

            products.setQuantity(products.getQuantity() + orderDetail.getQuantity());
            products.setSoldQuantity(products.getSoldQuantity() - orderDetail.getQuantity());

            if (products.getQuantity() <= 0) {
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

    public Orders createOrder(Orders order, boolean useCoinWallet) {
        if (useCoinWallet) {
            Optional<CoinWallets> coinWalletOpt = coinWalletsRepository.findByUserId(order.getUserId());
            if (coinWalletOpt.isPresent()) {
                CoinWallets coinWallet = coinWalletOpt.get();
                double maxDiscount = order.getTotalAmount() * 0.1; // 10% của totalAmount
                double discountApplied = Math.min(coinWallet.getBalance(), maxDiscount); // Trừ được tối đa 10% hoặc số xu có trong ví

                order.setTotalAmount(order.getTotalAmount() - discountApplied);
                coinWallet.setBalance(coinWallet.getBalance() - discountApplied);

                coinWalletsRepository.save(coinWallet); // Lưu số dư mới của ví
            }
        }
        return ordersRepository.save(order);
    }

    public Orders updateOrder(String id, OrderDTO order) {
//        Orders x = ordersRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
//        mapper.updateOrders(x, order);
//        return ordersRepository.save(x);
        Orders x = ordersRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        x.setOrderId(x.getOrderId());
        x.setOrderTime(x.getOrderTime());
        x.setTotalAmount(x.getTotalAmount());
        x.setAddress(x.getAddress());
        x.setUserId(x.getUserId());
        x.setStatus(order.getStatus());
        x.setCancelTime(x.getCancelTime());
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

    //@Transactional
    public boolean updateOrderStatus(String orderId, byte newStatusByte) {
        Optional<Orders> orderOpt = ordersRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            log.warn("Không tìm thấy đơn hàng với ID: {}", orderId);
            return false;
        }

        Orders order = orderOpt.get();
        OrderEnums currentStatus = OrderEnums.fromOrderStatus(order.getStatus());
        OrderEnums newStatus = OrderEnums.fromOrderStatus(newStatusByte);

        // Kiểm tra trạng thái hợp lệ
        if (!isValidStatusTransition(currentStatus, newStatus)) {
            log.warn("Chuyển trạng thái không hợp lệ: {} → {}", currentStatus, newStatus);
            return false;
        }

        // Lock đơn hàng nếu cần
        if (currentStatus == OrderEnums.UNORDERED && newStatus != OrderEnums.UNORDERED) {
            lockOrderDetails(order.getOrderId());
        }

        // Cộng tiền thưởng vào ví nếu giao hàng thành công
        if (newStatus == OrderEnums.DELIVERED) {
            Optional<CoinWallets> coinWalletOpt = coinWalletsRepository.findByUserId(order.getUserId());
            double rewardAmount = order.getTotalAmount() * 0.01; // 1% thưởng

            CoinWallets coinWallet = coinWalletOpt.orElseGet(() -> {
                log.info("Không tìm thấy ví cho userId: {}, tạo ví mới.", order.getUserId());
                CoinWallets newWallet = new CoinWallets();
                newWallet.setUserId(order.getUserId());
                newWallet.setBalance(0.0); // Khởi tạo số dư = 0
                return coinWalletsRepository.save(newWallet); // Lưu ví mới vào DB
            });

            coinWallet.setBalance(coinWallet.getBalance() + rewardAmount);
            coinWalletsRepository.save(coinWallet);
            log.info("Thêm {} vào ví của user {}.", rewardAmount, order.getUserId());
        }

        order.setStatus(newStatus.getOrder_status());
        ordersRepository.save(order);
        log.info("Cập nhật trạng thái đơn hàng {} thành {}", orderId, newStatus);
        return true;
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

    public List<MonthlyRevenueDTO> getMonthlyRevenue() {
        List<Orders> deliveredOrders = ordersRepository.findByStatus(OrderEnums.DELIVERED.getOrder_status());

        // Nhóm theo tháng và tính tổng doanh thu
        Map<String, Double> revenueMap = deliveredOrders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getOrderTime().toInstant().atZone(ZoneId.systemDefault()).getYear() + "-" +
                                order.getOrderTime().toInstant().atZone(ZoneId.systemDefault()).getMonthValue(),
                        Collectors.summingDouble(Orders::getTotalAmount)
                ));

        // Chuyển Map thành danh sách DTO
        return revenueMap.entrySet().stream()
                .map(entry -> {
                    String[] parts = entry.getKey().split("-");
                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    return new MonthlyRevenueDTO(month, year, entry.getValue());
                })
                .sorted(Comparator.comparing(MonthlyRevenueDTO::getYear)
                        .thenComparing(MonthlyRevenueDTO::getMonth))
                .collect(Collectors.toList());
    }

//    public List<Orders> getOrdersByEmailAndStatus(String email, byte status) {
//        return ordersRepository.findByEmailAndStatus(email, status);
//    }

    public List<HistoryOrderDTO> getHistoryOrder(String email) {
        Optional<Users> userOpt = userRepository.findByEmail(email);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        Users user = userOpt.get();
        String userId = user.getUserId();
        List<Orders> orders = ordersRepository.findByUserId(userId);

        List<HistoryOrderDTO> historyOrders = orders.stream().map(x -> {
            HistoryOrderDTO historyOrder = new HistoryOrderDTO();
            historyOrder.setOrderId(x.getOrderId());
            historyOrder.setOrderTime(x.getOrderTime());
            historyOrder.setTotalAmount(x.getTotalAmount());
            historyOrder.setStatus(x.getStatus());
            List<OrderDetails> orderDetails = orderDetailsRepository.findByOrderId(x.getOrderId());
            historyOrder.setQuantity(orderDetails.size());
            List<ProductDetailsDTO> productDetails = orderDetails.stream().map(detail -> {
                ProductDetailsDTO productDetail = new ProductDetailsDTO();
                Products products = productsRepository.findById(detail.getProductId()).get();
                if (products == null) {
                    throw new RuntimeException("Product not found");
                }
                productDetail.setProductName(products.getProductName());
                productDetail.setQuantity(detail.getQuantity());
                productDetail.setDiscountPrice(detail.getDiscountPrice());
                if (products.getProductImages() != null && !products.getProductImages().isEmpty()) {
                    productDetail.setImageUrl(products.getProductImages().get(0).getImageURL());
                } else {
                    productDetail.setImageUrl(null);
                }

                return productDetail;
            }).collect(Collectors.toList());
            historyOrder.setProductName(productDetails);
            return historyOrder;
        }).collect(Collectors.toList());
        return historyOrders;
    }

    public HistoryOrderDTO getOrderById(String email, String orderId) {
        Optional<Users> userOpt = userRepository.findByEmail(email);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        Users user = userOpt.get();
        String userId = user.getUserId();

        Optional<Orders> orderOpt = ordersRepository.findByOrderIdAndUserId(orderId, userId);
        if (!orderOpt.isPresent()) {
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

        List<ProductDetailsDTO> productDetails = orderDetails.stream().map(detail -> {
            ProductDetailsDTO productDetail = new ProductDetailsDTO();
            Products products = productsRepository.findById(detail.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

            productDetail.setProductName(products.getProductName());
            productDetail.setQuantity(detail.getQuantity());
            productDetail.setDiscountPrice(detail.getDiscountPrice());

            if (products.getProductImages() != null && !products.getProductImages().isEmpty()) {
                productDetail.setImageUrl(products.getProductImages().get(0).getImageURL());
            } else {
                productDetail.setImageUrl(null);
            }
            return productDetail;
        }).collect(Collectors.toList());
        historyOrder.setProductName(productDetails);
        return historyOrder;
    }
    public void deleteOrder(String email, String orderId) {
        Optional<Users> userOpt = userRepository.findByEmail(email);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        Users user = userOpt.get();
        String userId = user.getUserId();
//        Optional<Orders> orders = ordersRepository.findByUserIdAndStatus(userId, OrderEnums.PENDING.getOrder_status());
        Optional<Orders> orders = ordersRepository.findByOrderIdAndUserId(orderId, userId);
        if (!orders.isPresent()) {
            throw new RuntimeException("Order not found");
        }
        Orders order = orders.get();
        List<OrderDetails> orderDetails = orderDetailsRepository.findByOrderId(order.getOrderId());
//        Optional<OrderDetails> orderDetails = orderDetailsRepository.findByOrderIdAndUserId()
        Transactions transactions = transactionsRepository.findByOrderId(order.getOrderId());
        for(OrderDetails orderDetail : orderDetails) {
            Products products = productsRepository.findById(orderDetail.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
            products.setQuantity(products.getQuantity() + orderDetail.getQuantity());
            products.setSoldQuantity(products.getSoldQuantity() - orderDetail.getQuantity());
            productsRepository.save(products);
//            orderDetailsRepository.deleteAll(orderDetails);
        }

        orderDetailsRepository.deleteAll(orderDetails);

        ordersRepository.delete(order);

    }


    public List<Orders> sortOrdersByOrderTimeDESC(){
        return ordersRepository.findAllByOrderByOrderTimeDesc();
    }

    public List<Orders> sortOrdersByOrderTimeASC(){
        return ordersRepository.findAllByOrderByOrderTimeAsc();
    }

    public List<Orders> sortOrdersByEmailOrderTimeDESC(String email){
        String userId = userRepository.findByEmail(email).get().getUserId();
        if(userId == null) {
            throw new RuntimeException("User not found");
        }
        return ordersRepository.sortOrdersByUserIdAndOrderTimeDesc(userId);
    }

    public List<Orders> sortOrdersByEmailOrderTimeASC(String email){
        String userId = userRepository.findByEmail(email).get().getUserId();
        if(userId == null) {
            throw new RuntimeException("User not found");
        }
        return ordersRepository.sortOrdersByUserIdAndOrderTimeAsc(userId);
    }
}
