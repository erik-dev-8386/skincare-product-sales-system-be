package application.havenskin.controllers;

import application.havenskin.models.Discounts;
import application.havenskin.dataAccess.DiscountDTO;
import application.havenskin.services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/haven-skin/discounts")
public class DiscountController {
    @Autowired
    private DiscountService discountService;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Discounts> getAllDiscount(){
        return discountService.getAllDiscounts();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping
    public Discounts addDiscount(@RequestBody DiscountDTO discounts){
        return discountService.createDiscount(discounts);
    }
    @GetMapping("/{id}")
    public Discounts getDiscountById(@PathVariable String id){
        return discountService.getDiscountById(id);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PutMapping("/{id}")
    public Discounts updateDiscount(@PathVariable String id, @RequestBody DiscountDTO discount){
        return discountService.updateDiscount(id, discount);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @DeleteMapping("/{id}")
    public Discounts deleteDiscount(@PathVariable String id){
        return discountService.deleteDiscount(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping("/add-list-discounts")
    public List<Discounts> addListDiscount(@RequestBody List<Discounts> discounts){
        return discountService.addListDiscounts(discounts);
    }
    @GetMapping("/name/{discountName}")
    public Discounts getDiscountByName(@PathVariable String discountName){
        return discountService.getDiscountByName(discountName);
    }
    @GetMapping("/list-name-discounts")
    public List<String> getListDiscountByName(){
        return discountService.getDiscountsByName();
    }

}
