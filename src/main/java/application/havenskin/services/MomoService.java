package application.havenskin.services;

import application.havenskin.config.MomoConfig;
import application.havenskin.dataAccess.CreateMomoRequest;
import application.havenskin.dataAccess.CreateMomoResponse;
import application.havenskin.repositories.MomoRepository;
import application.havenskin.models.Orders;
import application.havenskin.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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
            throw new RuntimeException("Order not found");
        }

        log.info("SECRET_KEY: {}", momoConfig.getSecretKey());

        //String orderId = UUID.randomUUID().toString();
        String orderInfo = "Thanh toan don hang: orderId" + orderId;
        String requestId = UUID.randomUUID().toString();
        String extraData = "Khong co khuyen mai gi het";
        long amount = (long) order.getTotalAmount();

        String rawSignature = String.format(
                "accessKey=%s&amount=%s&extraData=%s&ipnUrl=%s&orderId=%s&orderInfo=%s&partnerCode=%s&redirectUrl=%s&requestId=%s&requestType=%s",
                momoConfig.getAccessKey(), amount, extraData, momoConfig.getIpnUrl(), orderId, orderInfo,
                momoConfig.getPartnerCode(), momoConfig.getReturnUrl(), requestId, momoConfig.getRequestType());

        String prettySignature = "";

        try {
            prettySignature = signHmacSHA256(rawSignature, momoConfig.getSecretKey());
        } catch (Exception e) {
            log.error("Lỗi khi hash code: ", e);
            return null;
        }

        if (prettySignature.isBlank()) {
            log.error("Signature is blank");
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
}