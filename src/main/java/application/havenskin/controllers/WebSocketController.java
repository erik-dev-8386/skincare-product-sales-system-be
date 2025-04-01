package application.havenskin.controllers;

import application.havenskin.dataAccess.SalesDataDTO;
import application.havenskin.services.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/web-socket")
public class WebSocketController {
    @Autowired
    private WebSocketService webSocketService;


    // lấy toàn bộ dữ liêu bán hàng theo tháng
    @GetMapping("/monthly")
    public List<SalesDataDTO> getMonthlyData() {
        return webSocketService.getMonthlySalesData();
    }

    // lấy dữ liệu bán hàng theo năm/tháng cụ thể
    @GetMapping("/monthly/filter")
    public List<SalesDataDTO> getMonthlyFilterData(@RequestParam int month,@RequestParam int year) {
        return  webSocketService.getMonthlySalesByYearAndMonth(year, month);
    }

    // endpoint Websocket để client có thể request dữ liệu

    @MessageMapping("/requestSalesData")
    @SendTo("/haven-skin/get/salesData")
    public List<SalesDataDTO> handleSalesDataRequest() {
        return webSocketService.getMonthlySalesData();
    }
    /**
     * Lấy tổng số lượng bán của một sản phẩm cụ thể trong một tháng
     */
    @GetMapping("/product-monthly")
    public int getProductSalesByMonth(
            @RequestParam String productName,
            @RequestParam int year,
            @RequestParam int month) {

        List<SalesDataDTO> monthlyData = webSocketService.getMonthlySalesByYearAndMonth(year, month);

        return monthlyData.stream()
                .filter(data -> data.getProductName().equals(productName))
                .mapToInt(SalesDataDTO::getTotalSales)
                .sum();
    }
    @PostMapping("/update-stats")
    public String updateSalesStatistics() {
        webSocketService.sendDataToWebSocket();
        return "Đã cập nhật thống kê bán hàng";
    }
}
