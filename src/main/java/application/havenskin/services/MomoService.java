package application.havenskin.services;

import application.havenskin.config.MomoConfig;
import application.havenskin.dataAccess.CreateMomoRequest;
import application.havenskin.dataAccess.CreateMomoResponse;
import application.havenskin.dataAccess.MomoIPNResponse;
import application.havenskin.enums.OrderEnums;
import application.havenskin.repositories.MomoRepository;
import application.havenskin.models.Orders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class MomoService {
    private final MomoConfig momoConfig;
    private final MomoRepository momoRepository;
    @Autowired
    private OrderService orderService;

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

        // Kiểm tra resultCode từ MoMo (0 là thanh toán thành công)
        if (ipnResponse.getResultCode() == 0) {
            log.info("Thanh toán thành công, cập nhật Order: {}", ipnResponse.getOrderId());

            // Cập nhật trạng thái đơn hàng thành PROCESSING
            boolean updated = orderService.updateOrderStatus(ipnResponse.getOrderId(), OrderEnums.PROCESSING.getOrder_status());
            log.info("Cập nhật đơn hàng trạng thái PROCESSING: {}", updated);

            if (!updated) {
                log.error("Cập nhật trạng thái đơn hàng thất bại");
                return false;
            }
            return true;
        } else {
            log.warn("Thanh toán thất bại, mã lỗi: {}", ipnResponse.getResultCode());
            return false;
        }
    }
}