package application.havenskin.controllers;

import application.havenskin.dataAccess.CreateMomoResponse;
import application.havenskin.services.MomoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/haven-skin/momo")
public class MomoController {
    private final MomoService momoService;

    @PostMapping("create/{orderId}")
    public CreateMomoResponse createQR(@PathVariable String orderId) {
        return momoService.createQR(orderId);
    }
}
