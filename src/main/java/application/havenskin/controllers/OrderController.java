package application.havenskin.controllers;

import application.havenskin.dataAccess.OrderDTO;
import application.havenskin.enums.OrderEnums;
import application.havenskin.models.Orders;
import application.havenskin.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/haven-skin/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PreAuthorize("hasAnyRole('ADMIN','STAFF', 'CUSTOMER')")
    @GetMapping
    public List<Orders> getAllOrder(){
        return orderService.getAllOrders();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping
    public Orders createOrder(@RequestBody Orders order){
        return orderService.createOrder(order);
    }
    @GetMapping("/id")
    public Orders getOrderById(@PathVariable String id){
        return orderService.getOrderById(id);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PutMapping("/id")
    public Orders updateOrder(@PathVariable  String id,@RequestBody OrderDTO order){
        return orderService.updateOrder(id, order);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @DeleteMapping("/id")
    public Orders deleteOrder(@PathVariable String id){
        return orderService.deleteOrder(id);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping("/add-list-orders")
    public List<Orders> addListOrder(@RequestBody List<Orders> orders){
        return orderService.addListOfOrders(orders);
    }

    @GetMapping("/{id}")
    public int ShowQuantityByOrderId(@PathVariable String id){
        return orderService.ShowQuantityByOrderId(id);
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable String id, @RequestParam byte status) {
        boolean updated = orderService.updateOrderStatus(id, status);
        if (updated) {
            return ResponseEntity.ok("Cập nhật trạng thái thành công");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không tìm thấy đơn hàng hoặc lỗi xảy ra");
    }
}
