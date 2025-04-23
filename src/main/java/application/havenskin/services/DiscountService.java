package application.havenskin.services;

import application.havenskin.dataAccess.DiscountDTO;
import application.havenskin.enums.DiscountEnum;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.Discounts;
import application.havenskin.repositories.DiscountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        //return discountsRepository.findActiveDiscountsSortedByName();
    }
    public Discounts getDiscountById(String id) {
        return discountsRepository.findByDiscountId(id);
    }
    public Discounts createDiscount(DiscountDTO discount) {
        if (discountsRepository.existsByDiscountName(discount.getDiscountName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tên khuyến mãi đã tồn tại!");
        }
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

    public List<Discounts> getDiscountsByName() {
        return discountsRepository.findByStatusOrderByCreatedTimeAsc((byte) 2);
    }

    public List<Discounts> searchDiscountName(String categoryName) {
        return discountsRepository.findByDiscountNameContaining(categoryName);
    }
}
