package application.havenskin.service;

import application.havenskin.BusinessObject.Models.Brands;
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
        return categoriesRepository.findById(id).get();
    }

    public Categories addCategories(Categories categories) {
        return categoriesRepository.save(categories);
    }

    public Categories updateCategory(String id, Categories category) {
        Categories existingCategory = categoriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        if(category.getCategoryName() != null){
            existingCategory.setCategoryName(category.getCategoryName());
        }
        if(category.getUsageInstruction() != null){
            existingCategory.setUsageInstruction(category.getUsageInstruction());
        }
        if(category.getDescription() != null){
            existingCategory.setDescription(category.getDescription());
        }
        return categoriesRepository.save(existingCategory);
    }

    public void deleteCategories(String id) {
        categoriesRepository.deleteById(id);
    }

    public Categories getCategoriesByName(String name) {
        if(categoriesRepository.findByCategoryName(name) == null){
            throw new RuntimeException("Category does not exist");
        }
        return categoriesRepository.findByCategoryName(name);
    }

}
