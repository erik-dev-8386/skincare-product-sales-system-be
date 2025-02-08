package application.havenskin.services;

import application.havenskin.models.Categories;
import application.havenskin.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (category.getStatus() != 0) {
            existingCategory.setStatus(category.getStatus());
        }
        return categoriesRepository.save(existingCategory);
    }

    public void softDeleteCategories(String id) {
        Categories categories = categoriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categories.setStatus((byte)0);
        categoriesRepository.save(categories);
    }

    public Categories getCategoriesByName(String name) {
        if(categoriesRepository.findByCategoryName(name) == null){
            throw new RuntimeException("Category does not exist");
        }
        return categoriesRepository.findByCategoryName(name);
    }

}
