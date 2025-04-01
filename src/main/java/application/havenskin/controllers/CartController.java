package application.havenskin.controllers;


import application.havenskin.dataAccess.CheckOutResponseDTO;
import application.havenskin.dataAccess.CheckoutRequestDTO;
import application.havenskin.dataAccess.HistoryOrderDTO;
import application.havenskin.models.OrderDetails;
import application.havenskin.services.OrderDetailsService;
import application.havenskin.services.OrderService;
import application.havenskin.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/cart")
public class CartController  {
    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/checkout")
    public CheckOutResponseDTO checkout(@RequestBody CheckoutRequestDTO checkoutRequestDTO){
        return orderService.checkout(checkoutRequestDTO);
    }

    @GetMapping("/{email}")
    public List<HistoryOrderDTO> getHistoryOrders(@PathVariable String email){
        return orderService.getHistoryOrder(email);
    }

    @GetMapping("/{email}/{orderId}")
    public HistoryOrderDTO getHistoryOrder(@PathVariable String email, @PathVariable String orderId){
        return orderService.getOrderById(email, orderId);
    }


    @DeleteMapping("/delete/{email}/{orderId}")
    public void deleteOrder(@PathVariable String email,@PathVariable String orderId){
        orderService.deleteOrder(email,orderId);
    }

    @GetMapping("/pay/{orderId}")
    public void succesPay(@PathVariable String orderId){
        transactionService.successTransactions(orderId);
    }


//    @GetMapping("/status/{email}")
//    public List<HistoryOrderDTO> getHistoryOrdersByStatus(@PathVariable String email){
//        return orderService.getOrdersByStatus(email);
//    }

}
