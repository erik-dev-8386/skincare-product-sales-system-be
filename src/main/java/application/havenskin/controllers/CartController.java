package application.havenskin.controllers;


import application.havenskin.dataAccess.CartItemsDTO;
import application.havenskin.models.OrderDetails;
import application.havenskin.services.OrderDetailsService;
import application.havenskin.services.OrderService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/cart")
public class CartController  {
    @Autowired
    private OrderDetailsService orderDetailsService;

    // hàm này được gọi khi ở trang sản phẩm người dùng bấm add-to-cart
    @PostMapping("/add")
    public OrderDetails addtoCart(@RequestParam String email, @RequestParam String productName){
        return orderDetailsService.addToCart(email, productName);
    }

    // hàm này được gọi khi ở trang sản phẩm người dùng bấm add-to-cart kèm theo quantity
    @PostMapping("/addWithQuantity")
    public OrderDetails addtoCartWithQuantity(@RequestParam String email, @RequestParam String productName, @RequestParam int quantity){
        return orderDetailsService.addToCartQuantity(email, productName, quantity);
    }

    //hàm này dùng để khi người dùng bấm dấu  "+" trong trang giỏ hàng
    @PutMapping("/increase")
    public OrderDetails increase(@RequestParam String email, @RequestParam String productName){
        return orderDetailsService.increaseQuantity(email, productName);
    }

    //hàm này dùng để khi người dùng bấm dấu  "-" trong trang giỏ hàng
    @PutMapping("/decrease")
    public OrderDetails decrease(@RequestParam String email, @RequestParam String productName){
        return orderDetailsService.decreaseQuantity(email, productName);
    }

    // hám này khi người dùng bấm nút remove ở giỏ hàng là sẽ xóa cái sp đó ko quan tâm quantity
    @DeleteMapping("/remove")
    public String removeFromCart(@RequestParam String email, @RequestParam String productName) {
        return orderDetailsService.removeFromCart(email, productName);
    }

    // hàm này show ra các sản phẩm trong giỏ hàng
    @GetMapping("/items")
    public List<CartItemsDTO> getCart(@RequestParam String email){
        return orderDetailsService.getCartItems(email);
    }


    // hàm này tính tổng tiền của giỏ hàng
    @GetMapping("/total")
    public double getTotal(@RequestParam String email){
        return orderDetailsService.calculateTotalPrice(email);
    }

    // còn hàm checkout  nx nhưng mà để tính cái nha!!!!!





}
