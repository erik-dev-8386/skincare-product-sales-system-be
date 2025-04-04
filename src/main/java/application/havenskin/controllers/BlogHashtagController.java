package application.havenskin.controllers;

import application.havenskin.models.BlogHashtag;
import application.havenskin.services.BlogHashtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/haven-skin/blog-hashtag")
public class BlogHashtagController {

    @Autowired
    private BlogHashtagService blogHashtagService;

    // Lấy tất cả các hashtag
    @GetMapping
    public List<BlogHashtag> getAllBlogHashtags() {
        return blogHashtagService.getAll();
    }

    // Lấy hashtag theo ID
//    @GetMapping("/{id}")
//    public BlogHashtag getBlogHashtagById(@PathVariable String id) {
//        return blogHashtagService.getById(id);
//    }

    // Lấy hashtag theo tên
    @GetMapping("/name/{name}")
    public Optional<BlogHashtag> getBlogHashtagByName(@RequestParam String name) {
        return blogHashtagService.getByHashtagName(name);
    }

    // Thêm một hashtag mới
    @PostMapping
    public BlogHashtag addBlogHashtag(@RequestBody BlogHashtag blogHashtag) {
        return blogHashtagService.addBlogHashtag(blogHashtag);
    }

    // Cập nhật hashtag theo tên
    @PutMapping("/{blogHashtagName}")
    public BlogHashtag updateBlogHashtag(@PathVariable String blogHashtagName, @RequestBody BlogHashtag blogHashtag) {
        return blogHashtagService.updateBlogHashtagByHashtagName(blogHashtagName, blogHashtag);
    }

    @PreAuthorize("hasRole('ADMIN')")
    // Xóa mềm hashtag theo tên
    @DeleteMapping("/{blogHashtagName}")
    public String deleteBlogHashtag(@PathVariable String blogHashtagName) {
        return blogHashtagService.deleteBlogHashtagByHashtagName(blogHashtagName);
    }
}
