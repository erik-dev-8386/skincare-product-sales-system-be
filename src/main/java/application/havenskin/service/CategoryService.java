package application.havenskin.service;

import application.havenskin.BusinessObject.Models.Categories;
import application.havenskin.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoriesRepository categoriesRepository;
    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }
    public Categories getCategoriesById(String id) {
        return categoriesRepository.getById(id);
    }
    public Categories addCategories(Categories categories) {
        return categoriesRepository.save(categories);
    }
    public Categories updateCategories(String id, Categories categories) {
        if(getCategoriesById(id) == null){
            throw new RuntimeException("Category does not exist");
        }
        return categoriesRepository.save(categories);
    }
    public void deleteCategories(String id) {
        categoriesRepository.deleteById(id);
    }
    public String getCategoriesByName(String name) {
        if(categoriesRepository.findBycategoryName(name) == null){
            throw new RuntimeException("Category does not exist");
        }
        return categoriesRepository.findBycategoryName(name).getCategoryId();
    }

}
