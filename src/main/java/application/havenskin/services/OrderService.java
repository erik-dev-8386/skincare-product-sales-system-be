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

    public void saveOrder(Orders order) {
        ordersRepository.save(order);
    }

    public List<Orders> getAllPendingOrders() {
        return ordersRepository.findByStatus(OrderEnums.PENDING.getOrder_status());
    }


    // hàm này khi khách hàng bấm nút đặt hàng!
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
            order.setStatus(OrderEnums.UNORDERED.getOrder_status());
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
            // *******************
            //products.setQuantity(products.getQuantity() - cartItemDTO.getQuantity());
            //products.setSoldQuantity(products.getSoldQuantity() + cartItemDTO.getQuantity());
            // *****************
//            if (products.getQuantity() <= 0) {
//                products.setStatus(ProductEnums.OUT_OF_STOCK.getValue());
//            }
//            productsRepository.save(products);
            // **********************
        }
        order.setTotalAmount(totalOrderPrice);
        ordersRepository.save(order);

        order.setStatus(OrderEnums.UNORDERED.getOrder_status());
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

//            Transactions transactions = new Transactions();
//            transactions.setOrderId(order.getOrderId());
//            transactions.setTransactionTime(LocalDateTime.now());
////            transactions.setAmount(detail.getQuantity());
//            transactionsRepository.save(transactions);
            return temp;
        }).collect(Collectors.toList()));
        return response;
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
            if(products.getQuantity() > 0){
                products.setStatus(ProductEnums.AVAILABLE.getValue());
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
//        return ordersRepository.findAll();
        return ordersRepository.findByStatusNot((byte) OrderEnums.UNORDERED.getOrder_status());
    }

    public Orders getOrderById(String id) {
        return ordersRepository.getById(id);
    }

    public Orders createOrder(Orders order) {  return ordersRepository.save(order);}

    public Orders updateOrder(String id, OrderDTO order) {
//        Orders x = ordersRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
//        mapper.updateOrders(x, order);
//        return ordersRepository.save(x);
        Orders x = ordersRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        x.setOrderId(x.getOrderId());
        x.setOrderTime(x.getOrderTime());
//        if(x.getTotalAmount() == 0) {
//            x.setTotalAmount(x.getTotalAmount());
//        }
//        else {
//            x.setTotalAmount(order.getTotalAmount());
//        }
        x.setTotalAmount(x.getTotalAmount());
        x.setAddress(x.getAddress());
        x.setUserId(x.getUserId());
        x.setCancelTime(x.getCancelTime());

        if(isValidStatusOrder(OrderEnums.fromOrderStatus(x.getStatus()),OrderEnums.fromOrderStatus(order.getStatus()))){
            x.setStatus(order.getStatus());
            return ordersRepository.save(x);
        }
        else{
            throw new RuntimeException("Chuyển trạng thái không hợp lệ");
        }
    }


    public Orders updateOrderAmount(String id, OrderDTO order) {
//        Orders x = ordersRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
//        mapper.updateOrders(x, order);
//        return ordersRepository.save(x);
        Orders x = ordersRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        x.setOrderId(x.getOrderId());
        x.setOrderTime(x.getOrderTime());
        if(x.getTotalAmount() == 0) {
            x.setTotalAmount(x.getTotalAmount());
        }
        else {
            x.setTotalAmount(order.getTotalAmount());
        }
        x.setAddress(x.getAddress());
        x.setUserId(x.getUserId());
        x.setCancelTime(x.getCancelTime());
        x.setStatus(x.getStatus());
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

    @Transactional
    public boolean updateOrderStatus(String orderId, byte newStatusByte) {
        Optional<Orders> orderOpt = ordersRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            log.warn("Không tìm thấy đơn hàng với ID: {}", orderId);
            return false;
        }

        Orders order = orderOpt.get();
        OrderEnums currentStatus = OrderEnums.fromOrderStatus(order.getStatus());
        OrderEnums newStatus = OrderEnums.fromOrderStatus(newStatusByte);

        if (!isValidStatusOrder(currentStatus, newStatus)) {
            log.warn("Chuyển trạng thái không hợp lệ: {} → {}", currentStatus, newStatus);
            return false;
        }

        // Lock chi tiết đơn hàng nếu cần
        if (currentStatus == OrderEnums.UNORDERED && newStatus != OrderEnums.UNORDERED) {
            lockOrderDetails(order.getOrderId());
        }

        // Tạo giao dịch và cập nhật kho khi chuyển từ UNORDERED -> PROCESSING
        if (currentStatus == OrderEnums.UNORDERED && newStatus == OrderEnums.PROCESSING) {
            // Tạo giao dịch
            String transactionCode = UUID.randomUUID().toString(); // hoặc lấy từ request nếu có
            transactionService.createTransaction(
                    orderId,
                    order.getTotalAmount(),
                    TransactionsEnums.PAID.getValue(),
                    transactionCode
            );

            // Lấy chi tiết đơn hàng
            List<OrderDetails> orderDetailsList = orderDetailsRepository.findByOrderId(orderId);
            for (OrderDetails detail : orderDetailsList) {
                Optional<Products> productOpt = productsRepository.findById(detail.getProductId());
                if (productOpt.isPresent()) {
                    Products product = productOpt.get();
                    int orderedQuantity = detail.getQuantity();

                    // Trừ tồn kho
                    if (product.getQuantity() < orderedQuantity) {
                        log.warn("Sản phẩm {} không đủ hàng. Tồn kho: {}, yêu cầu: {}",
                                product.getProductName(), product.getQuantity(), orderedQuantity);
                        throw new IllegalStateException("Sản phẩm không đủ hàng để xử lý đơn.");
                    }

                    product.setQuantity(product.getQuantity() - orderedQuantity);
                    product.setSoldQuantity(product.getSoldQuantity() + orderedQuantity);

                    // Kiểm tra nếu số lượng bằng 0, cập nhật trạng thái OUT_OF_STOCK
                    if (product.getQuantity() == 0) {
                        product.setStatus(ProductEnums.OUT_OF_STOCK.getValue());
                        log.info("Sản phẩm {} đã hết hàng và được cập nhật trạng thái là OUT_OF_STOCK.", product.getProductName());
                    }

                    productsRepository.save(product);
                } else {
                    log.warn("Không tìm thấy sản phẩm với ID: {}", detail.getProductId());
                }
            }
        }

        // Cộng tiền vào ví khi giao thành công
        if (newStatus == OrderEnums.DELIVERED) {
            Optional<CoinWallets> coinWalletOpt = coinWalletsRepository.findByUserId(order.getUserId());
            double rewardAmount = order.getTotalAmount() * 0.01;

            CoinWallets coinWallet = coinWalletOpt.orElseGet(() -> {
                log.info("Không tìm thấy ví cho userId: {}, tạo ví mới.", order.getUserId());
                CoinWallets newWallet = new CoinWallets();
                newWallet.setUserId(order.getUserId());
                newWallet.setBalance(0.0);
                return coinWalletsRepository.save(newWallet);
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

    public boolean isValidStatusOrder(OrderEnums currentStatus, OrderEnums newStatus) {
        Map<OrderEnums, List<OrderEnums>> validTransitions = new HashMap<>();

        validTransitions.put(OrderEnums.UNORDERED, List.of(OrderEnums.PENDING, OrderEnums.PROCESSING, OrderEnums.CANCELLED));
        validTransitions.put(OrderEnums.PENDING, List.of(OrderEnums.PROCESSING, OrderEnums.SHIPPING, OrderEnums.CANCELLED));
        validTransitions.put(OrderEnums.PROCESSING, List.of(OrderEnums.SHIPPING, OrderEnums.CANCELLED));
        validTransitions.put(OrderEnums.SHIPPING, List.of(OrderEnums.DELIVERED, OrderEnums.RETURNED));
        validTransitions.put(OrderEnums.DELIVERED, List.of());
        validTransitions.put(OrderEnums.CANCELLED, List.of());
        validTransitions.put(OrderEnums.RETURNED, List.of());

        if (newStatus.getOrder_status() < currentStatus.getOrder_status()) {
            return false;
        }

        return validTransitions.getOrDefault(currentStatus, List.of()).contains(newStatus);
    }

    public List<MonthlyRevenueDTO> getMonthlyRevenue() {
        List<Orders> deliveredOrders = ordersRepository.
                findByStatus(OrderEnums.DELIVERED.getOrder_status());

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


    public List<HistoryOrderDTO> getHistoryOrder(String email) {
        Optional<Users> userOpt = userRepository.findByEmail(email);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        Users user = userOpt.get();
        String userId = user.getUserId();
        List<Orders> orders = ordersRepository.findListOrderByStatus(userId, OrderEnums.UNORDERED.getOrder_status());
        if(orders.isEmpty()) {
            throw new RuntimeException("Order not found");
        }
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
        for (OrderDetails orderDetail : orderDetails) {
            Products products = productsRepository.findById(orderDetail.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
            products.setQuantity(products.getQuantity() + orderDetail.getQuantity());
            products.setSoldQuantity(products.getSoldQuantity() - orderDetail.getQuantity());
            productsRepository.save(products);
//            orderDetailsRepository.deleteAll(orderDetails);
        }

        orderDetailsRepository.deleteAll(orderDetails);

        ordersRepository.delete(order);

    }


    public List<Orders> sortOrdersByOrderTimeDESC() {
        return ordersRepository.findAllByOrderByOrderTimeDesc();
    }

    public List<Orders> sortOrdersByOrderTimeASC() {
        return ordersRepository.findAllByOrderByOrderTimeAsc();
    }


    public List<HistoryOrderDTO> sortOrdersByOrderTimeDESC(String email) {
        Optional<Users> userOpt = userRepository.findByEmail(email);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        Users user = userOpt.get();
        String userId = user.getUserId();

        // Lấy danh sách đơn hàng đã sắp xếp
        List<Orders> sortedOrders = ordersRepository.findByUserIdAndStatusNotOrderByOrderTimeDesc(userId, OrderEnums.UNORDERED.getOrder_status());
//        List<Orders> sortedOrders = ordersRepository.findByUserIdAndStatusNotOrderByOrderTimeDesc(userId, OrderEnums.UNORDERED.getOrder_status());

        // Chuyển đổi sang DTO với thông tin sản phẩm
        return sortedOrders.stream().map(order -> {
            HistoryOrderDTO dto = new HistoryOrderDTO();
            dto.setOrderId(order.getOrderId());
            dto.setOrderTime(order.getOrderTime());
            dto.setTotalAmount(order.getTotalAmount());
            dto.setStatus(order.getStatus());

            List<OrderDetails> orderDetails = orderDetailsRepository.findByOrderId(order.getOrderId());
            dto.setQuantity(orderDetails.size());

            List<ProductDetailsDTO> productDetails = orderDetails.stream().map(detail -> {
                ProductDetailsDTO productDetail = new ProductDetailsDTO();
                Products product = productsRepository.findById(detail.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                productDetail.setProductName(product.getProductName());
                productDetail.setQuantity(detail.getQuantity());
                productDetail.setDiscountPrice(detail.getDiscountPrice());

                if (product.getProductImages() != null && !product.getProductImages().isEmpty()) {
                    productDetail.setImageUrl(product.getProductImages().get(0).getImageURL());
                } else {
                    productDetail.setImageUrl(null);
                }

                return productDetail;
            }).collect(Collectors.toList());

            dto.setProductName(productDetails);
            return dto;
        }).collect(Collectors.toList());
    }

    public List<HistoryOrderDTO> sortOrdersByOrderTimeASC(String email) {
        Optional<Users> userOpt = userRepository.findByEmail(email);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        Users user = userOpt.get();
        String userId = user.getUserId();

        // Lấy danh sách đơn hàng đã sắp xếp
        List<Orders> sortedOrders = ordersRepository.findByUserIdAndStatusNotOrderByOrderTimeDesc(userId, OrderEnums.UNORDERED.getOrder_status());

        // Chuyển đổi sang DTO với thông tin sản phẩm
        return sortedOrders.stream().map(order -> {
            HistoryOrderDTO dto = new HistoryOrderDTO();
            dto.setOrderId(order.getOrderId());
            dto.setOrderTime(order.getOrderTime());
            dto.setTotalAmount(order.getTotalAmount());
            dto.setStatus(order.getStatus());

            List<OrderDetails> orderDetails = orderDetailsRepository.findByOrderId(order.getOrderId());
            dto.setQuantity(orderDetails.size());

            List<ProductDetailsDTO> productDetails = orderDetails.stream().map(detail -> {
                ProductDetailsDTO productDetail = new ProductDetailsDTO();
                Products product = productsRepository.findById(detail.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                productDetail.setProductName(product.getProductName());
                productDetail.setQuantity(detail.getQuantity());
                productDetail.setDiscountPrice(detail.getDiscountPrice());

                if (product.getProductImages() != null && !product.getProductImages().isEmpty()) {
                    productDetail.setImageUrl(product.getProductImages().get(0).getImageURL());
                } else {
                    productDetail.setImageUrl(null);
                }

                return productDetail;
            }).collect(Collectors.toList());

            dto.setProductName(productDetails);
            return dto;
        }).collect(Collectors.toList());
    }

}
