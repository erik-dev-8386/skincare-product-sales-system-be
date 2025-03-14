package application.havenskin.controllers;

import application.havenskin.dataAccess.OrderDetailDTO;
import application.havenskin.models.OrderDetails;
import application.havenskin.services.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/haven-skin/order-details")
public class OrderDetailsController {
    @Autowired
    private OrderDetailsService orderDetailsService;
    @PreAuthorize("hasAnyRole('ADMIN','STAFF', 'CUSTOMER')")
    @GetMapping
    public List<OrderDetails> getOrderDetails() {
        return orderDetailsService.getAllOrderDetails();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping
    public OrderDetails createOrderDetails(@RequestBody OrderDetails orderDetails) {
        return orderDetailsService.addOrderDetails(orderDetails);
    }
    @GetMapping("/{id}")
    public OrderDetails getOrderDetailsById(@PathVariable String id) {
        return orderDetailsService.getOrderDetailsByID(id);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PutMapping("/{id}")
    public OrderDetails updateOrderDetails(@PathVariable String id, @RequestBody OrderDetailDTO orderDetails) {
        return orderDetailsService.updateOrderDetails(id, orderDetails);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @DeleteMapping("/{id}")
    public OrderDetails deleteOrderDetailsByID(@PathVariable String id) {
        return orderDetailsService.deleteOrderDetails(id);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping("/add-list-order-details")
    public List<OrderDetails> addOrderDetailsList(@RequestBody List<OrderDetails> orderDetailsList) {
        return orderDetailsService.addListOfOrderDetails(orderDetailsList);
    }
}
