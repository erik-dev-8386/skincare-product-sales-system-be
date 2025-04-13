package application.havenskin.services;

import application.havenskin.dataAccess.SalesDataDTO;
import application.havenskin.repositories.OrderDetailsRepository;
import application.havenskin.repositories.OrdersRepository;
import application.havenskin.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebSocketService {
    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public List<SalesDataDTO> getMonthlySalesData(){
        // lấy  dữ liệu bán hàng theo tháng
       List<Object[]> monthlySalesData = orderDetailsRepository.findMonthlyProductSales();

       List<SalesDataDTO> salesData = new ArrayList<>();


       for (Object[] row : monthlySalesData) {
           SalesDataDTO salesDataDTO = new SalesDataDTO(
                   (String) row[2], // tên sản phẩm
                   (Integer) row[1], // tháng
                   (Integer) row[0], // năm
                   ((Number) row[3]).intValue() //tổng số lượng bán
           );
           salesData.add(salesDataDTO);
       }
       return salesData;
    }

    // gửi data theo tháng qua WebSocket
    public void sendDataToWebSocket(){
        List<SalesDataDTO> salesData = getMonthlySalesData();

        // gửi dữ liệu tới endpoint của WebSocket
        messagingTemplate.convertAndSend(
                "/haven-skin/get/salesData", // Đường dẫn WebSocket
                getMonthlySalesData() // Dữ liệu gửi đi
        );
    }

    // Lấy dữ liệu bán hàng theo time cụ thể
    public List<SalesDataDTO> getMonthlySalesByYearAndMonth(int year, int month){

        // Lấy dữ liệu theo tháng
        List<Object[]> monthlySalesData = orderDetailsRepository.findMonthlyProductSales();

        List<SalesDataDTO> list = new ArrayList<>();

        for (Object[] data:monthlySalesData ) {
            if((Integer) data[0] == year &&  (Integer) data[1] == month){
                SalesDataDTO salesDataDTO = new SalesDataDTO(
                        (String) data[2],     // Tên sản phẩm
                        (Integer) data[1],    // Tháng
                        (Integer) data[0],    // Năm
                        ((Number) data[3]).intValue() // Tổng số lượng bán
                );
                list.add(salesDataDTO);

            }
        }
        return list;
    }

}
