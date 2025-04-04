package application.havenskin.services;

import application.havenskin.config.MomoConfig;
import application.havenskin.dataAccess.CreateMomoRequest;
import application.havenskin.dataAccess.CreateMomoResponse;
import application.havenskin.dataAccess.MomoIPNResponse;
import application.havenskin.enums.OrderEnums;
import application.havenskin.enums.TransactionsEnums;
import application.havenskin.models.Users;
import application.havenskin.repositories.MomoRepository;
import application.havenskin.models.Orders;
import application.havenskin.repositories.OrdersRepository;
import application.havenskin.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class MomoService {
    private final MomoConfig momoConfig;
    private final MomoRepository momoRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private UserRepository userRepository;
    public void testConfig() {
        log.info("SECRET_KEY: {}", momoConfig.getSecretKey());
    }

    public CreateMomoResponse createQR(String orderId) {
        // Lấy thông tin đơn hàng từ orderId
        Orders order = orderService.getOrderById(orderId);
        if (order == null) {
            log.error("Order not found: {}", orderId);
            throw new RuntimeException("Order not found");
        }

        log.info("Tạo QR cho đơn hàng: {}", orderId);

        String orderInfo = "Thanh toán đơn hàng: " + orderId;
        String requestId = UUID.randomUUID().toString();
        String extraData = "Không có khuyến mãi";
        long amount = (long) order.getTotalAmount();

        String rawSignature = String.format(
                "accessKey=%s&amount=%s&extraData=%s&ipnUrl=%s&orderId=%s&orderInfo=%s&partnerCode=%s&redirectUrl=%s&requestId=%s&requestType=%s",
                momoConfig.getAccessKey(), amount, extraData, momoConfig.getIpnUrl(), orderId, orderInfo,
                momoConfig.getPartnerCode(), momoConfig.getReturnUrl(), requestId, momoConfig.getRequestType());

        String prettySignature;
        try {
            prettySignature = signHmacSHA256(rawSignature, momoConfig.getSecretKey());
        } catch (Exception e) {
            log.error("Lỗi khi tạo chữ ký: ", e);
            return null;
        }

        if (prettySignature.isBlank()) {
            log.error("Chữ ký tạo ra bị trống");
            return null;
        }

        CreateMomoRequest request = CreateMomoRequest.builder()
                .partnerCode(momoConfig.getPartnerCode())
                .requestType(momoConfig.getRequestType())
                .ipnUrl(momoConfig.getIpnUrl())
                .redirectUrl(momoConfig.getReturnUrl())
                .orderId(orderId)
                .orderInfo(orderInfo)
                .requestId(requestId)
                .extraData(extraData)
                .amount(amount)
                .signature(prettySignature)
                .lang("vi")
                .build();

        return momoRepository.createMomoQR(request);
    }

    private String signHmacSHA256(String data, String key) throws Exception {
        Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretkey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        hmacSHA256.init(secretkey);
        byte[] hash = hmacSHA256.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public boolean handleMomoIPN(MomoIPNResponse ipnResponse) {
        log.info("Nhận IPN từ MoMo: {}", ipnResponse);

        if (ipnResponse == null || ipnResponse.getOrderId() == null) {
            log.error("IPN không hợp lệ, thiếu orderId");
            return false;
        }

        String orderId = ipnResponse.getOrderId();
        double amount = ipnResponse.getAmount();
        String requestId = ipnResponse.getRequestId(); // transactionCode
        String partnerCode = momoConfig.getPartnerCode();

        // Xác định trạng thái thanh toán
        boolean isPaid = ipnResponse.getResultCode() == 0;
        TransactionsEnums transactionStatus = isPaid ? TransactionsEnums.PAID : TransactionsEnums.NOT_PAID;

        // Tạo Transaction
        transactionService.createTransaction(
                orderId,
                amount,
                transactionStatus.getValue(),
                requestId // transactionCode
        );
        List<Orders> orders = ordersRepository.findByOrderId(orderId);
        Optional<Users> users = userRepository.findById(orders.get(0).getUserId());
        String email = users.get().getEmail();

        // Nếu thanh toán thành công, cập nhật trạng thái đơn hàng
        if (isPaid) {
            boolean updated = orderService.updateOrderStatus(orderId, OrderEnums.PROCESSING.getOrder_status());
            sendOrderConfirmationEmail(email, orderId, amount);

            if (!updated) {
                log.warn("Cập nhật trạng thái đơn hàng thất bại cho orderId: {}", orderId);
            }
        }

        return true;
    }
//    private void sendOrderConfirmationEmail(String to, String orderId, double totalAmount) {
//        String subject = "Haven Skin - Xác nhận bạn đã thanh toán thành công cho đơn hàng #" + orderId;
//
//        String emailContent =
//                "Cảm ơn bạn đã đặt hàng tại Haven Skin!\n\n" +
//                        "Đơn hàng của bạn đã được thanh toán thành công.\n\n" +
//                        "Thông tin đơn hàng:\n" +
//                        "- Mã đơn hàng: #" + orderId + "\n" +
//                        "- Tổng tiền: " + String.format("%,.0f VND", totalAmount) + "\n\n" +
//                        "Nếu có bất kỳ thắc mắc nào, vui lòng liên hệ:\n" +
//                        "- Hotline: 0966340303\n" +
//                        "- Email: havenskin032025@gmail.com\n\n" +
//                        "Trân trọng,\n" +
//                        "Đội ngũ Haven Skin";
//
//        emailService.sendEmail(to, subject, emailContent);
//    }
private void sendOrderConfirmationEmail(String to, String orderId, double totalAmount) {
    // Tiêu đề email
    String subject = "Haven Skin - Xác nhận thanh toán thành công đơn hàng #" + orderId;

    // Nội dung email được format đẹp
//    String emailContent =
//            "=============================================\n" +
//                    "               HAVEN SKIN                    \n" +
//                    "=============================================\n\n" +
//                    "          XÁC NHẬN THANH TOÁN THÀNH CÔNG          \n\n" +
//                    "Cảm ơn bạn đã đặt hàng tại Haven Skin!\n" +
//                    "Đơn hàng của bạn đã được thanh toán thành công.\n\n" +
//                    "---------------------------------------------\n" +
//                    "THÔNG TIN ĐƠN HÀNG:\n" +
//                    "---------------------------------------------\n" +
//                    "• Mã đơn hàng: #" + orderId + "\n" +
//                    "• Tổng tiền:   " + String.format("%,.0f VND", totalAmount) + "\n\n" +
//                    "---------------------------------------------\n" +
//                    "Nếu có bất kỳ thắc mắc nào, vui lòng liên hệ:\n" +
//                    "• Hotline: 0966340303\n" +
//                    "• Email: havenskin032025@gmail.com\n\n" +
//                    "Trân trọng,\n" +
//                    "Đội ngũ Haven Skin\n" +
//                    "=============================================";
    String emailContent =
            "╔══════════════════════════════════════════╗\n" +
                    "║              HAVEN SKIN                                                                       ║\n" +
                    "╠══════════════════════════════════════════╣\n" +
                    "║     XÁC NHẬN THANH TOÁN THÀNH CÔNG                                  ║\n" +
                    "╚══════════════════════════════════════════╝\n\n" +
                    "Cảm ơn quý khách đã lựa chọn Haven Skin!\n" +
                    "Đơn hàng của bạn đã được xử lý thành công.\n\n" +

                    "┌──────────────────────────────────────────┐\n" +
                    "│          THÔNG TIN ĐƠN HÀNG                                                         │\n" +
                    "├──────────────────────────────────────────┤\n" +
                    "│  ▪ Mã đơn hàng: #" + String.format("%-36s", orderId) +"         │\n" +
                    "│  ▪ Tổng thanh toán: " + String.format("%,.0f VND%-15s", totalAmount,"                                                        │") + "\n" +
                    "└──────────────────────────────────────────┘\n\n" +

                    "Hỗ trợ khách hàng:\n" +
                    "• Hotline: 0966 340 303 (8:00-21:00)\n" +
                    "• Email: havenskin032025@gmail.com\n" +
                    "• Website: localhost:5173/\n\n" +

                    "════════════════════════════════════════════\n" +
                    "Trân trọng,\n" +
                    "Đội ngũ chăm sóc khách hàng Haven Skin\n" +
                    "════════════════════════════════════════════";
    // Gửi email
    emailService.sendEmail(to, subject, emailContent);
}


}