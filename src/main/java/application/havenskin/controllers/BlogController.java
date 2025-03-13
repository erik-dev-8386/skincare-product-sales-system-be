package application.havenskin.controllers;

import application.havenskin.models.Blogs;
import application.havenskin.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;

    // Lấy tất cả blogs
    @GetMapping
    public List<Blogs> getAllBlogs() {
        return blogService.getAllBlogs();
    }

    // Lấy blog theo ID
//    @GetMapping("/id/{id}")
//    public Blogs getBlogById(@PathVariable String id) {
//        return blogService.getBlogById(id);
//    }

    // Tìm blog theo tiêu đề
    @GetMapping(value = "/title/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Blogs getBlogByTitle(@PathVariable String title) {
        return blogService.getBlogByTitle(title);
    }

    // Tìm blog theo danh mục
    @GetMapping("/category/{categoryName}")
    public List<Blogs> getBlogByCategory(@PathVariable String categoryName) {
        return  blogService.getBlogByCategory(categoryName);
    }

    // Tìm blog theo hashtag
    @GetMapping("/hashtag/{hashtagName}")
    public List<Blogs> getBlogsByHashtag(@PathVariable String hashtagName) {
        return blogService.getBlogsByHashtagName(hashtagName);
    }

    // Tạo mới blog
    @PostMapping
    public Blogs createBlog(@RequestBody Blogs blog) {
        return blogService.createBlog(blog);
    }

    // Cập nhật blog theo ID
    @PutMapping("/{blogTitle}")
    public Blogs updateBlogById(@PathVariable String blogTitle, @RequestBody Blogs blog) {
        return blogService.updateBlogByTitle(blogTitle, blog);
    }

    // Xóa mềm blog theo ID
    @DeleteMapping("/{blogTitle}")
    public String deleteBlogById(@PathVariable String blogTitle) {
        return blogService.deleteBlogByTitle(blogTitle);
    }
}
