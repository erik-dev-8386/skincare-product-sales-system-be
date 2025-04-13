package application.havenskin.controllers;

import application.havenskin.dataAccess.HistoryOrderDTO;
import application.havenskin.dataAccess.MonthlyRevenueDTO;
import application.havenskin.dataAccess.OrderDTO;
import application.havenskin.dataAccess.UserDTO;
import application.havenskin.enums.OrderEnums;
import application.havenskin.models.Orders;
import application.havenskin.models.Users;
import application.havenskin.services.OrderService;
import application.havenskin.services.UsersService;
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
//    @PreAuthorize("hasAnyRole('ADMIN','STAFF', 'CUSTOMER')")

    @Autowired
    private UsersService usersService;
    @GetMapping
    public List<Orders> getAllOrder(){
        return orderService.getAllOrders();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping
    public Orders createOrder(@RequestBody Orders order) {
        return orderService.createOrder(order);
    }
    @GetMapping("/search/{id}")
    public List<Orders> searchOrderById(@PathVariable String id){
        return orderService.getOrdersList(id);
    }

    @GetMapping("/{id}")
    public Orders getOrderById(@PathVariable String id){
        return orderService.getOrderById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PutMapping("/{id}")
    public Orders updateOrder(@PathVariable  String id,@RequestBody OrderDTO order){
        return orderService.updateOrder(id, order);
    }
//    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PutMapping("/order-amount/{id}")
    public Orders updateAmount(@PathVariable  String id,@RequestBody OrderDTO order){
        return orderService.updateOrderAmount(id, order);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @DeleteMapping("/{id}")
    public Orders deleteOrder(@PathVariable String id){
        return orderService.deleteOrder(id);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping("/add-list-orders")
    public List<Orders> addListOrder(@RequestBody List<Orders> orders){
        return orderService.addListOfOrders(orders);
    }

    @GetMapping("/revenue/monthly")
    public List<MonthlyRevenueDTO> getMonthlyRevenue() {
        return orderService.getMonthlyRevenue();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable String id, @RequestParam byte status) {
        boolean updated = orderService.updateOrderStatus(id, status);
        if (updated) {
            return ResponseEntity.ok("Cập nhật trạng thái thành công");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không tìm thấy đơn hàng hoặc lỗi xảy ra");
    }
    @DeleteMapping("/cancel-order/{email}/{orderId}")
    public void cancelOrder(@PathVariable String email, @PathVariable String orderId) {
        orderService.cancelOrder(email, orderId);
    }

    @GetMapping("/sort-desc")
    public List<Orders> sortDesc(){
        return orderService.sortOrdersByOrderTimeASC();
    }
    @GetMapping("/sort-asc")
    public List<Orders> sortAsc(){
        return orderService.sortOrdersByOrderTimeASC();
    }


    @GetMapping("/sort-desc/{email}")
    public List<HistoryOrderDTO> sortDesc(@PathVariable String email){
        return orderService.sortOrdersByOrderTimeDESC(email);
    }

    @GetMapping("/sort-asc/{email}")
    public List<HistoryOrderDTO> sortAsc(@PathVariable String email){
        return orderService.sortOrdersByOrderTimeASC(email);
    }

    // hàm này cập nhập thông tin khách hàng!!!
    @PostMapping("/check-out/{email}/{orderId}")
    public Orders checkOutUser(@PathVariable String email, @PathVariable String orderId, @RequestBody UserDTO userDTO){
        return usersService.checkOutUser(email,orderId, userDTO);
    }
}
