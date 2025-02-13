package application.havenskin.services;

import application.havenskin.dataAccess.CategoryDTO;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.Categories;
import application.havenskin.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private Mapper mapper;
    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }
    public Categories getCategoriesById(String id) {
        return categoriesRepository.getById(id);
    }
    public Categories addCategories(Categories categories) {
        return categoriesRepository.save(categories);
    }
    public Categories updateCategories(String id, CategoryDTO categories) {
       Categories x = categoriesRepository.findById(id).orElseThrow(()->new RuntimeException("Category not found"));
       mapper.updateCategories(x, categories);
       return categoriesRepository.save(x);
    }
//    public void deleteCategories(String id) {
//        categoriesRepository.deleteById(id);
//    }
//    public String getCategoriesByName(String name) {
//        if(categoriesRepository.findBycategoryName(name) == null){
//            throw new RuntimeException("Category does not exist");
//        }
//        return categoriesRepository.findBycategoryName(name).getCategoryId();
//    }
public Categories softDeleteCategories(String id) {
    Categories categories = categoriesRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));
    categories.setStatus((byte)0);
    categoriesRepository.save(categories);
    return categories;
}

    public Categories getCategoriesByName(String name) {
        if(categoriesRepository.findByCategoryName(name) == null){
            throw new RuntimeException("Category does not exist");
        }
        return categoriesRepository.findByCategoryName(name);
    }
    public List<Categories> addListOfCategory(List<Categories> categoriesList) {
        return categoriesRepository.saveAll(categoriesList);
    }
}
