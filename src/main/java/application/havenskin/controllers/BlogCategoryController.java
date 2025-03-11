package application.havenskin.controllers;

import application.havenskin.models.BlogCategory;
import application.havenskin.services.BlogCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/blogCategory")
public class BlogCategoryController {
    @Autowired
    private BlogCategoryService blogCategoryService;

    // Lấy tất cả Blog Categories
    @GetMapping
    public List<BlogCategory> getAllBlogCategories() {
        return blogCategoryService.getAll();
    }

    // Lấy Blog Category theo tên
    @GetMapping("/name/{name}")
    public BlogCategory getByBlogCategoryName(@PathVariable String name) {
        return blogCategoryService.getByBlogCategoryName(name);
    }

    @GetMapping("/id/{id}")
    public BlogCategory getByBlogCategoryId(@PathVariable String id) {
        return blogCategoryService.getByBlogCategoryId(id);
    }

    // Thêm mới Blog Category
    @PostMapping
    public BlogCategory addBlogCategory(@RequestBody BlogCategory blogCategory) {
        return blogCategoryService.addBlogCategory(blogCategory);
    }

    // Cập nhật Blog Category theo tên
    @PutMapping("/{id}")
    public BlogCategory updateBlogCategoryById(@PathVariable String id, @RequestBody BlogCategory blogCategory) {
        return blogCategoryService.updateBlogCategoryById(id, blogCategory);
    }

    // Xóa mềm Blog Category theo tên
    @DeleteMapping("/{id}")
    public String deleteBlogCategoryById(@PathVariable String id) {
        return blogCategoryService.deleteBlogCategoryById(id);
    }
}
