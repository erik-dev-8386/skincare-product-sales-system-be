package application.havenskin.controller;

import application.havenskin.BusinessObject.Models.Categories;
import application.havenskin.response.Response;
import application.havenskin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public Response<List<Categories>> getAllCategories() {
        Response<List<Categories>> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(categoryService.getAllCategories());
        return response;
    }
    @PostMapping
    public Response<Categories> addCategory(@RequestBody Categories categories) {
        Response<Categories> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(categoryService.addCategories(categories));
        return response;
    }
    @GetMapping("/id/{id}")
    public Response<Categories> getCategoryById(@PathVariable String id) {
        Response<Categories> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(categoryService.getCategoriesById(id));
        return response;
    }
    @GetMapping("/name/{categoryName}")
    public Response<Categories> getCategoryByName(@PathVariable String categoryName) {
        Response<Categories> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(categoryService.getCategoriesByName(categoryName));
        return response;
    }
    @PutMapping("/{id}")
    public Response<Categories> updateCategory(@PathVariable String id, @RequestBody Categories category) {
        Response<Categories> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(categoryService.updateCategory(id, category));
        return response;
    }
    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable String id) {
        categoryService.deleteCategories(id);
        return "Category has been deleled successfully";
    }
}

