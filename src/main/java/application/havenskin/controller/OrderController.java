package application.havenskin.controller;

import application.havenskin.BusinessObject.Models.Orders;
import application.havenskin.DTORequest.OrderDTO;
import application.havenskin.response.Response;
import application.havenskin.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping
    public Response<List<Orders>> getAllOrder(){
        Response<List<Orders>> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(orderService.getAllOrders());
        return response;
    }
    @PostMapping
    public Response<Orders> createOrder(@RequestBody Orders order){
        Response<Orders> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(orderService.createOrder(order));
        return response;
    }
    @GetMapping("/id")
    public Response<Orders> getOrderById(@PathVariable String id){
        Response<Orders> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(orderService.getOrderById(id));
        return response;
    }

    @PutMapping("/id")
    public Response<Orders> updateOrder(@PathVariable  String id,@RequestBody OrderDTO order){
        Response<Orders> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(orderService.updateOrder(id, order));
        return response;
    }
    @DeleteMapping("/id")
    public void deleteOrder(@PathVariable String id){
        orderService.deleteOrder(id);
    }
    @PostMapping("/add-list-order")
    public Response<List<Orders>> addListOrder(@RequestBody List<Orders> orders){
        Response<List<Orders>> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(orderService.addListOfOrders(orders));
        return response;
    }

    @GetMapping("/{id}")
    public int ShowQuantityByOrderId(@PathVariable String id){
        return orderService.ShowQuantityByOrderId(id);
    }
}
