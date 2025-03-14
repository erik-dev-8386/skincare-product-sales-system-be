package application.havenskin.controllers;

import application.havenskin.dataAccess.CreateMomoResponse;
import application.havenskin.dataAccess.MomoIPNResponse;
import application.havenskin.services.MomoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/haven-skin/momo")
public class MomoController {
    private final MomoService momoService;

    @PostMapping("create/{orderId}")
    public CreateMomoResponse createQR(@PathVariable String orderId) {
        return momoService.createQR(orderId);
    }

    @PostMapping("/ipn-handler")
    public ResponseEntity<String> handleMomoIPN(@RequestBody MomoIPNResponse ipnResponse) {
        System.out.println("Nhận IPN từ MoMo: {} " + ipnResponse.getOrderId());

        boolean success = momoService.handleMomoIPN(ipnResponse);
        if (success) {
            return ResponseEntity.ok("Cập nhật đơn hàng thành công");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cập nhật đơn hàng thất bại");
        }
    }
}
