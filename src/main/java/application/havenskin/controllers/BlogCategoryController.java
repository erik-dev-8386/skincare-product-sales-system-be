package application.havenskin.controllers;

import application.havenskin.models.BlogCategory;
import application.havenskin.services.BlogCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    // Lấy Blog Category theo tên, FE truyền về name của category
    @GetMapping("/name/{name}")
    public Optional<BlogCategory> getByBlogCategoryName(@PathVariable String name) {
        return blogCategoryService.getByBlogCategoryName(name);
    }

//    @GetMapping("/id/{id}")
//    public BlogCategory getByBlogCategoryId(@PathVariable String id) {
//        return blogCategoryService.getByBlogCategoryId(id);
//    }

    // Thêm mới Blog Category
    @PostMapping
    public BlogCategory addBlogCategory(@RequestBody BlogCategory blogCategory) {
        return blogCategoryService.addBlogCategory(blogCategory);
    }

    // Cập nhật Blog Category theo tên
    @PutMapping("/{blogCategoryName}")
    public BlogCategory updateBlogCategory(@PathVariable String blogCategoryName, @RequestBody BlogCategory blogCategory) {
        return blogCategoryService.updateBlogCategoryByName(blogCategoryName, blogCategory);
    }

    @PreAuthorize("hasRole('ADMIN')")
    // Xóa mềm Blog Category theo tên
    @DeleteMapping("/{blogCategoryName}")
    public String deleteBlogCategory(@PathVariable String blogCategoryName) {
        return blogCategoryService.deleteBlogCategoryByName(blogCategoryName);
    }
}
