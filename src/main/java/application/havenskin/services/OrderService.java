package application.havenskin.services;

import application.havenskin.dataAccess.OrderDTO;
import application.havenskin.enums.OrderDetailEnums;
import application.havenskin.enums.OrderEnums;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.CoinWallets;
import application.havenskin.models.OrderDetails;
import application.havenskin.models.Orders;
import application.havenskin.repositories.CoinWalletsRepository;
import application.havenskin.repositories.OrderDetailsRepository;
import application.havenskin.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private CoinWalletsRepository coinWalletsRepository;
    @Autowired
    private Mapper mapper;

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


}
