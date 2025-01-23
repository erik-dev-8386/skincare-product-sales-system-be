package application.havenskin.controller;

import application.havenskin.BusinessObject.Models.Orders;
import application.havenskin.response.Response;
import application.havenskin.service.OrderService;
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
    public Response<List<Orders>> getAllOrder(){
        Response<List<Orders>> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(orderService.getAllOrders());
        return response;
    }
}
