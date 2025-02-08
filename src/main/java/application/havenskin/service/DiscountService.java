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

    public Discounts getDiscountByName(String name) {
        return discountsRepository.findByDiscountName(name);
    }
    public Discounts createDiscount(Discounts discount) {
        return discountsRepository.save(discount);
    }
    public Discounts updateDiscount(String id,Discounts discount) {
        Discounts existingDiscount = discountsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount not found"));
        if (discount.getDiscountName() != null) {
            existingDiscount.setDiscountName(discount.getDiscountName());
        }
        if (discount.getActualEndTime() != null) {
            existingDiscount.setActualEndTime(discount.getActualEndTime());
        }
        if (discount.getActualStartTime() != null) {
            existingDiscount.setActualStartTime(discount.getActualStartTime());
        }
        if (discount.getDiscountCode() != null) {
            existingDiscount.setDiscountCode(discount.getDiscountCode());
        }
        if (discount.getDiscountPercent() != 0) {
            existingDiscount.setDiscountPercent(discount.getDiscountPercent());
        }
        if (discount.getDescription() != null) {
            existingDiscount.setDescription(discount.getDescription());
        }
        if (discount.getDeletedTime() != null) {
            existingDiscount.setDeletedTime(discount.getDeletedTime());
        }
        if (discount.getStatus() != 0) {
            existingDiscount.setStatus(discount.getStatus());
        }
        if (discount.getCreatedTime() != null) {
            existingDiscount.setCreatedTime(discount.getCreatedTime());
        }

        return discountsRepository.save(existingDiscount);
    }
    public void deleteDiscount(String id) {
        if(getDiscountById(id) == null) {
            throw new RuntimeException("Discount not found");
        }
        discountsRepository.delete(getDiscountById(id));
    }
}
