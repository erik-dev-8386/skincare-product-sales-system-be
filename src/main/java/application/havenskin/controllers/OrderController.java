package application.havenskin.controllers;

import application.havenskin.models.Orders;
import application.havenskin.dataAccess.OrderDTO;
import application.havenskin.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/haven-skin/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping
    public List<Orders> getAllOrder(){
        return orderService.getAllOrders();
    }
    @PostMapping
    public Orders createOrder(@RequestBody Orders order){
        return orderService.createOrder(order);
    }
    @GetMapping("/id")
    public Orders getOrderById(@PathVariable String id){
        return orderService.getOrderById(id);
    }

    @PutMapping("/id")
    public Orders updateOrder(@PathVariable  String id,@RequestBody OrderDTO order){
        return orderService.updateOrder(id, order);
    }
    @DeleteMapping("/id")
    public Orders deleteOrder(@PathVariable String id){
        return orderService.deleteOrder(id);
    }
    @PostMapping("/add-list-orders")
    public List<Orders> addListOrder(@RequestBody List<Orders> orders){
        return orderService.addListOfOrders(orders);
    }

    @GetMapping("/{id}")
    public int ShowQuantityByOrderId(@PathVariable String id){
        return orderService.ShowQuantityByOrderId(id);
    }
}
