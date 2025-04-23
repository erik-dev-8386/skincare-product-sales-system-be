package application.havenskin.services;

import application.havenskin.dataAccess.CategoryDTO;
import application.havenskin.enums.CategoryEnums;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.Categories;
import application.havenskin.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private Mapper mapper;
    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    //    return categoriesRepository.findActiveCategorySortedByName();
    }
    public Categories getCategoriesById(String id) {
        return categoriesRepository.getById(id);
    }
    public Categories addCategories(CategoryDTO categories) {
        // Kiểm tra tên danh mục sản phẩm có bị trùng không
        if (categoriesRepository.existsByCategoryName(categories.getCategoryName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tên danh mục sản phẩm đã tồn tại!");
        }
        Categories x = mapper.toCategories(categories);
        return categoriesRepository.save(x);
    }
    public Categories updateCategories(String id, CategoryDTO categories) {
        Categories x = categoriesRepository.findById(id).orElseThrow(()->new RuntimeException("Category not found"));
        mapper.updateCategories(x, categories);
        return categoriesRepository.save(x);
    }
    public Categories deleteCategories(String id) {
        Optional<Categories> x = categoriesRepository.findById(id);
        if (x.isPresent()) {
            Categories categories = x.get();
            categories.setStatus(CategoryEnums.INACTIVE.getStatus());
            return categoriesRepository.save(categories);
        }
        throw new RuntimeException("Category not found");
    }
    public List<Categories> addListOfCategory(List<Categories> categoriesList) {
        return categoriesRepository.saveAll(categoriesList);
    }
    public String getCategoriesByName(String name) {
        if(categoriesRepository.findByCategoryName(name) == null){
            throw new RuntimeException("Category does not exist");
        }
        return categoriesRepository.findByCategoryName(name).getCategoryId();
    }
    public List<Categories> getAllCategoriesNames() {
        return categoriesRepository.findByStatusOrderByCategoryNameAsc((byte) 2);
    }

    public List<Categories> searchByName(String name) {
        return categoriesRepository.findByCategoryNameContaining(name);
    }
}
