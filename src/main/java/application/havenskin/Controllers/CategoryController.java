package application.havenskin.controllers;

import application.havenskin.dataAccess.CategoryDTO;
import application.havenskin.models.Categories;
import application.havenskin.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public List<Categories> getAllCategories() {
        return categoryService.getAllCategories();
    }
    @PostMapping
    public Categories addCategory(@RequestBody Categories categories) {
        return categoryService.addCategories(categories);
    }
    @GetMapping("/{id}")
    public Categories getCategoryById(@PathVariable String id) {
       return categoryService.getCategoriesById(id);
    }
    @PutMapping("/{id}")
    public Categories updateCategory(@PathVariable String id, @RequestBody CategoryDTO categories) {
        return categoryService.updateCategories(id, categories);
    }
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable String id) {
        categoryService.deleteCategories(id);
    }

    @PostMapping("/add-list-category")
    public List<Categories> addListCategory(@RequestBody List<Categories> categories) {
        return categoryService.addListOfCategory(categories);
    }
}

