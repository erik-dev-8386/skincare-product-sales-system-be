package application.havenskin.controller;

import application.havenskin.BusinessObject.Models.Discounts;
import application.havenskin.response.Response;
import application.havenskin.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/discounts")
public class DiscountController {
    @Autowired
    private DiscountService discountService;
    @GetMapping
    public List<Discounts> getAllDiscount(){
        return discountService.getAllDiscounts();
    }

    @PostMapping
    public Discounts addDiscount(@RequestBody Discounts discount){
        return discountService.createDiscount(discount);
    }

    @GetMapping("/id/{id}")
    public Discounts getDiscountById(@PathVariable String id){
        return discountService.getDiscountById(id);
    }

    @GetMapping("/name/{discountName}")
    public Discounts getDiscountByName(@PathVariable String discountName){
        return discountService.getDiscountByName(discountName);
    }

    @PutMapping("/{id}")
    public Discounts updateDiscount(@PathVariable String id, @RequestBody Discounts discount){
        return discountService.updateDiscount(id, discount);
    }

    @DeleteMapping("/{id}")
    public String deleteDiscount(@PathVariable String id){
        discountService.deleteDiscount(id);
        return "Discount has been deleted successfully";
    }
}
