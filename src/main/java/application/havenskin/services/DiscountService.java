package application.havenskin.services;

import application.havenskin.models.Discounts;
import application.havenskin.dataAccess.DiscountDTO;
import application.havenskin.enums.DiscountEnum;
import application.havenskin.mapper.Mapper;
import application.havenskin.repositories.DiscountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscountService {
    @Autowired
    private DiscountsRepository discountsRepository;
    @Autowired
    private Mapper mapper;
    public List<Discounts> getAllDiscounts() {
        return discountsRepository.findAll();
    }
    public Discounts getDiscountById(String id) {
        return discountsRepository.findByDiscountId(id);
    }
    public Discounts createDiscount(DiscountDTO discount) {
        Discounts x = mapper.toDiscounts(discount);
        return discountsRepository.save(x);
    }
    public Discounts updateDiscount(String id, DiscountDTO discount) {
        Discounts x = discountsRepository.findByDiscountId(id);
        if(x == null) {
            throw new RuntimeException("Discount not found");
        }
        mapper.updateDiscounts(x, discount);
        return discountsRepository.save(x);
    }
    public Discounts deleteDiscount(String id) {
//        if(getDiscountById(id) == null) {
//            throw new RuntimeException("Discount not found");
//        }
//        discountsRepository.delete(getDiscountById(id));
        Optional<Discounts> x = discountsRepository.findById(id);
        if(x.isPresent()) {
            Discounts discount = x.get();
            discount.setStatus(DiscountEnum.DISABLED.getDiscount_status());
            return discountsRepository.save(discount);
        }
        return null;
    }
    public List<Discounts> addListDiscounts(List<Discounts> discounts) {
        return discountsRepository.saveAll(discounts);
    }

    public String getDiscountByName(String name) {
        return discountsRepository.findByDiscountName(name).getDiscountId();
    }

    public List<String> getDiscountsByName() {
        return discountsRepository.findAllDiscountByName();
    }
}
