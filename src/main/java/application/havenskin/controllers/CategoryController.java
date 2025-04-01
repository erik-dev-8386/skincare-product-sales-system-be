package application.havenskin.controllers;

import application.havenskin.dataAccess.CategoryDTO;
import application.havenskin.models.Categories;
import application.havenskin.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/haven-skin/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // @PreAuthorize("hasAnyRole('ADMIN','STAFF', 'CUSTOMER')")
    @GetMapping
    public List<Categories> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping
    public Categories addCategory(@Valid @RequestBody CategoryDTO categories) {
        return categoryService.addCategories(categories);
    }

    @GetMapping("/{id}")
    public Categories getCategoryById(@PathVariable String id) {
        return categoryService.getCategoriesById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PutMapping("/{id}")
    public Categories updateCategory(@PathVariable String id, @RequestBody CategoryDTO categories) {
        return categoryService.updateCategories(id, categories);
    }

    //    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public Categories deleteCategory(@PathVariable String id) {
        return categoryService.deleteCategories(id);
    }

    @GetMapping("/name/{categoryName}")
    public String getCategoryByName(@PathVariable String categoryName) {
        return categoryService.getCategoriesByName(categoryName);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping("/add-list-categories")
    public List<Categories> addListCategory(@RequestBody List<Categories> categories) {
        return categoryService.addListOfCategory(categories);
    }

    @GetMapping("/list-name-categories")
    public List<Categories> listCategoryName() {
        return categoryService.getAllCategoriesNames();
    }

    @GetMapping("/search/{categoriesName}")
    public List<Categories> searchCategory(@PathVariable String categoriesName) {
        return categoryService.searchByName(categoriesName);
    }
}

