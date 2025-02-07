package application.havenskin.controller;

import application.havenskin.BusinessObject.Models.OrderDetails;
import application.havenskin.DTORequest.OrderDetailDTO;
import application.havenskin.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/order-details")
public class OrderDetailsController {
    @Autowired
    private OrderDetailsService orderDetailsService;

    @GetMapping
    public List<OrderDetails> getOrderDetails() {
        return orderDetailsService.getAllOrderDetails();
    }
    @PostMapping
    public OrderDetails createOrderDetails(@RequestBody OrderDetails orderDetails) {
        return orderDetailsService.addOrderDetails(orderDetails);
    }
    @GetMapping("/{id}")
    public OrderDetails getOrderDetailsById(@PathVariable String id) {
       return orderDetailsService.getOrderDetailsByID(id);
    }
    @PutMapping("/{id}")
    public OrderDetails updateOrderDetails(@PathVariable String id, @RequestBody OrderDetailDTO orderDetails) {
       return orderDetailsService.updateOrderDetails(id, orderDetails);
    }
    @DeleteMapping("/{id}")
    public OrderDetails deleteOrderDetailsByID(@PathVariable String id) {
        return orderDetailsService.deleteOrderDetails(id);
    }
    @PostMapping("/add-list-order-details")
    public List<OrderDetails> addOrderDetailsList(@RequestBody List<OrderDetails> orderDetailsList) {
       return orderDetailsService.addListOfOrderDetails(orderDetailsList);
    }
}
