package application.havenskin.controller;

import application.havenskin.BusinessObject.Models.Discounts;
import application.havenskin.DTORequest.DiscountDTO;
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
    @GetMapping("/{id}")
    public Discounts getDiscountById(@PathVariable String id){
        return discountService.getDiscountById(id);
    }
    @PutMapping("/{id}")
    public Discounts updateDiscount(@PathVariable String id, @RequestBody DiscountDTO discount){
        return discountService.updateDiscount(id, discount);
    }
    @DeleteMapping("/{id}")
    public Discounts deleteDiscount(@PathVariable String id){
        return discountService.deleteDiscount(id);
    }

    @PostMapping("/add-list-discount")
    public List<Discounts> addListDiscount(@RequestBody List<Discounts> discounts){
       return discountService.addListDiscounts(discounts);
    }
}
