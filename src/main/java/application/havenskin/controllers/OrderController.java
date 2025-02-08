package application.havenskin.controllers;

import application.havenskin.models.Orders;
import application.havenskin.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping
    public List<Orders> getAllOrder(){
        return orderService.getAllOrders();
    }
}
