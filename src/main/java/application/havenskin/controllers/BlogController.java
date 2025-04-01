package application.havenskin.controllers;

import application.havenskin.models.Blogs;
import application.havenskin.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/haven-skin/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;

    // Lấy tất cả blogs
    // *
    @GetMapping
    public List<Blogs> getAllBlogs() {
        return blogService.getAllBlogs();
    }

    // Lấy blog theo ID
//    @GetMapping("/id/{id}")
//    public Blogs getBlogById(@PathVariable String id) {
//        return blogService.getBlogById(id);
//    }

    // *
    // Tìm blog theo tiêu đề
    @GetMapping(value = "/title/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Blogs getBlogByTitle(@PathVariable String title) {
        return blogService.getBlogByTitle(title);
    }

    // *
    // Tìm blog theo danh mục
    @GetMapping("/category/{categoryName}")
    public List<Blogs> getBlogByCategory(@PathVariable String categoryName) {
        return  blogService.getBlogByCategory(categoryName);
    }
    // *
    // Tìm blog theo hashtag
    @GetMapping("/hashtag/{hashtagName}")
    public List<Blogs> getBlogsByHashtag(@PathVariable String hashtagName) {
        return blogService.getBlogsByHashtagName(hashtagName);
    }

    // *
    // Tạo mới blog
    @PostMapping
    public Blogs createBlog(@RequestPart("blogs") Blogs blog,@RequestPart("email") String email, @RequestParam("images") List<MultipartFile> images) throws IOException {
        return blogService.createBlog(blog, email, images);
    }

    // Cập nhật blog theo title
//    @PutMapping("/{blogTitle}")
//    public Blogs updateBlogById(@PathVariable String blogTitle, @RequestBody Blogs blog,@RequestParam(value = "images",required = false) List<MultipartFile> images ) throws IOException {
//        return blogService.updateBlogByTitle(blogTitle, blog, images);
//    }
    @PutMapping("/{blogTitle}")
    public Blogs updateBlogByTitle(
            @PathVariable String blogTitle,
            @RequestPart("blogs") Blogs blog,
            @RequestPart("email") String email,
            @RequestParam(value = "images", required = false) List<MultipartFile> images
    ) throws IOException {
        System.out.println("Received request to update blog: " + blogTitle);
        System.out.println("Status in request: " + blog.getStatus());

        Blogs updatedBlog = blogService.updateBlogByTitle(blogTitle, blog, email, images);
        System.out.println("Updated blog status: " + updatedBlog.getStatus());

        return updatedBlog;
    }

    @PreAuthorize("hasRole('ADMIN')")
    // Xóa mềm blog theo title
    @DeleteMapping("/{blogTitle}")
    public String deleteBlogById(@PathVariable String blogTitle) {
        return blogService.deleteBlogByTitle(blogTitle);
    }
}
