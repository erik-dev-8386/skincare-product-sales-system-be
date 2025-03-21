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

    // hàm này được gọi khi ở trang sản phẩm người dùng bấm add-to-cart
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','CUSTOMER')")
    @PostMapping("/add")
    public OrderDetails addtoCart(@RequestParam String email, @RequestParam String productName){
        return orderDetailsService.addToCart(email, productName);
    }

    // hàm này được gọi khi ở trang sản phẩm người dùng bấm add-to-cart kèm theo quantity
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','CUSTOMER')")
    @PostMapping("/addWithQuantity")
    public OrderDetails addtoCartWithQuantity(@RequestParam String email, @RequestParam String productName, @RequestParam int quantity){
        return orderDetailsService.addToCartQuantity(email, productName, quantity);
    }

    //hàm này dùng để khi người dùng bấm dấu  "+" trong trang giỏ hàng
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','CUSTOMER')")
    @PutMapping("/increase")
    public OrderDetails increase(@RequestParam String email, @RequestParam String productName){
        return orderDetailsService.increaseQuantity(email, productName);
    }

    //hàm này dùng để khi người dùng bấm dấu  "-" trong trang giỏ hàng
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','CUSTOMER')")
    @PutMapping("/decrease")
    public OrderDetails decrease(@RequestParam String email, @RequestParam String productName){
        return orderDetailsService.decreaseQuantity(email, productName);
    }

    // hám này khi người dùng bấm nút remove ở giỏ hàng là sẽ xóa cái sp đó ko quan tâm quantity
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','CUSTOMER')")
    @DeleteMapping("/remove")
    public String removeFromCart(@RequestParam String email, @RequestParam String productName) {
        return orderDetailsService.removeFromCart(email, productName);
    }

    // hàm này show ra các sản phẩm trong giỏ hàng
//    @PreAuthorize("hasAnyRole('ADMIN','STAFF','CUSTOMER')")
//    @GetMapping("/items")
//    public List<CartItemsDTO> getCart(@RequestParam String email){
//        return orderDetailsService.getCartItems(email);
//    }


    // hàm này tính tổng tiền của giỏ hàng
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','CUSTOMER')")
    @GetMapping("/total")
    public double getTotal(@RequestParam String email){
        return orderDetailsService.calculateTotalPrice(email);
    }

    // còn hàm checkout  nx nhưng mà để tính cái nha!!!!!

    // ko trả gì về cho FE đâu vì gửi request fe có thông tin hết r!!!
    // fe gửi nguyên list sp ở giỏ hàng về cho be xử lý lưu xún DB
//    @PostMapping("/checkout")
//    public void checkout(@RequestBody CheckoutRequestDTO checkoutRequestDTO){
//        orderService.checkout(checkoutRequestDTO);
//    }
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


}
