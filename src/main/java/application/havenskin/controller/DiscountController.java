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
    public Response<List<Discounts>> getAllDiscount(){
        Response<List<Discounts>> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(discountService.getAllDiscounts());
        return response;
    }
    @PostMapping
    public Response<Discounts> addDiscount(@RequestBody Discounts discount){
        Response<Discounts> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(discountService.createDiscount(discount));
        return response;
    }
    @GetMapping("/id/{id}")
    public Response<Discounts> getDiscountById(@PathVariable String id){
        Response<Discounts> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(discountService.getDiscountById(id));
        return response;
    }

    @GetMapping("/name/{discountName}")
    public Response<Discounts> getDiscountByName(@PathVariable String discountName){
        Response<Discounts> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(discountService.getDiscountByName(discountName));
        return response;
    }

    @PutMapping("/{id}")
    public Response<Discounts> updateDiscount(@PathVariable String id, @RequestBody Discounts discount){
        Response<Discounts> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(discountService.updateDiscount(id, discount));
        return response;
    }
    @DeleteMapping("/{id}")
    public String deleteDiscount(@PathVariable String id){
        discountService.deleteDiscount(id);
        return "Discount has been deleted successfully";
    }
}
