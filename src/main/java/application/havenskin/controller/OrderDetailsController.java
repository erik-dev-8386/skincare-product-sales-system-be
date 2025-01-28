package application.havenskin.controller;

import application.havenskin.BusinessObject.Models.OrderDetails;
import application.havenskin.DTORequest.OrderDetailDTO;
import application.havenskin.response.Response;
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
    public Response<List<OrderDetails>> getOrderDetails() {
        Response<List<OrderDetails>> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(orderDetailsService.getAllOrderDetails());
        return response;
    }
    @PostMapping
    public Response<OrderDetails> createOrderDetails(@RequestBody OrderDetails orderDetails) {
        Response<OrderDetails> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(orderDetailsService.addOrderDetails(orderDetails));
        return response;
    }
    @GetMapping("/{id}")
    public Response<OrderDetails> getOrderDetailsById(@PathVariable String id) {
        Response<OrderDetails> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(orderDetailsService.getOrderDetailsByID(id));
        return response;
    }
    @PutMapping("/{id}")
    public Response<OrderDetails> updateOrderDetails(@PathVariable String id, @RequestBody OrderDetailDTO orderDetails) {
        Response<OrderDetails> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(orderDetailsService.updateOrderDetails(id, orderDetails));
        return response;
    }
    @DeleteMapping
    public void deleteOrderDetailsByID(@PathVariable String id) {
        orderDetailsService.deleteOrderDetails(id);
    }
    @PostMapping("/add-list-order-details")
    public Response<List<OrderDetails>> addOrderDetailsList(@RequestBody List<OrderDetails> orderDetailsList) {
        Response<List<OrderDetails>> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(orderDetailsService.addListOfOrderDetails(orderDetailsList));
        return response;
    }
}
