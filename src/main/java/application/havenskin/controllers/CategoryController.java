package application.havenskin.controllers;

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

    @GetMapping("/id/{id}")
    public Categories getCategoryById(@PathVariable String id) {
        return categoryService.getCategoriesById(id);
    }

    @GetMapping("/name/{categoryName}")
    public Categories getCategoryByName(@PathVariable String categoryName) {
        return categoryService.getCategoriesByName(categoryName);
    }

    @PutMapping("/{id}")
    public Categories updateCategory(@PathVariable String id, @RequestBody Categories category) {
        return categoryService.updateCategory(id, category);
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable String id) {
        categoryService.softDeleteCategories(id);
        return "Category has been deleled successfully";
    }
}

