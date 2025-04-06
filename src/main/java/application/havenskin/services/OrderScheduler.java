package application.havenskin.services;

import application.havenskin.enums.OrderEnums;
import application.havenskin.models.Orders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderScheduler {

    private final OrderService orderService;

    @Scheduled(fixedRate = 3600000) // 1 giờ (1h = 3600000ms)
    public void cancelExpiredPendingOrders() {
        log.info("Chạy kiểm tra đơn hàng PENDING quá hạn...");

        List<Orders> pendingOrders = orderService.getAllPendingOrders();

        LocalDateTime now = LocalDateTime.now();

        for (Orders order : pendingOrders) {
            if (order.getOrderTime() == null) continue;

            LocalDateTime orderTime = order.getOrderTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            if (Duration.between(orderTime, now).toHours() >= 24) {
                order.setStatus(OrderEnums.CANCELLED.getOrder_status());
                order.setContent("Hết thời gian thanh toán");
                order.setCancelTime(new Date());
                orderService.saveOrder(order);
                log.info("Đơn hàng {} đã bị huỷ vì quá hạn", order.getOrderId());
            }
        }
    }
}
