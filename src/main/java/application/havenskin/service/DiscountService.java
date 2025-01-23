package application.havenskin.service;

import application.havenskin.BusinessObject.Models.Discounts;
import application.havenskin.repository.DiscountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountService {
    @Autowired
    private DiscountsRepository discountsRepository;
    public List<Discounts> getAllDiscounts() {
        return discountsRepository.findAll();
    }
    public Discounts getDiscountById(String id) {
        return discountsRepository.findByDiscountId(id);
    }
    public Discounts createDiscount(Discounts discount) {
        return discountsRepository.save(discount);
    }
    public Discounts updateDiscount(String id,Discounts discount) {
        if(getDiscountById(id) == null) {
            throw new RuntimeException("Discount not found");
        }
        return discountsRepository.save(discount);
    }
    public void deleteDiscount(String id) {
        if(getDiscountById(id) == null) {
            throw new RuntimeException("Discount not found");
        }
        discountsRepository.delete(getDiscountById(id));
    }
}
