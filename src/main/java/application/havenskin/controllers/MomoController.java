package application.havenskin.controllers;

import application.havenskin.dataAccess.CreateMomoResponse;
import application.havenskin.dataAccess.MomoIPNResponse;
import application.havenskin.services.MomoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/haven-skin/momo")
public class MomoController {
    private final MomoService momoService;

    @Autowired
    public MomoController(MomoService momoService) {
        this.momoService = momoService;
    }

    @PostMapping("/create/{orderId}")
    public ResponseEntity<CreateMomoResponse> createQR(@PathVariable String orderId) {
        CreateMomoResponse response = momoService.createQR(orderId);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/ipn-handler-new")
    public ResponseEntity<String> handleMomoIPN(@RequestBody MomoIPNResponse ipnResponse) {
        log.info("Nhận IPN từ MoMo: {}", ipnResponse); // Kiểm tra request từ MoMo

        if (ipnResponse == null || ipnResponse.getOrderId() == null) {
            log.error("IPN không hợp lệ");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("IPN không hợp lệ");
        }

        boolean success = momoService.handleMomoIPN(ipnResponse);
        if (success) {
            return ResponseEntity.ok("Cập nhật đơn hàng thành công");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cập nhật đơn hàng thất bại");
        }
    }

}
