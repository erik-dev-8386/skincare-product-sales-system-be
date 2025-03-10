package application.havenskin.controllers;

import application.havenskin.models.BlogHashtag;
import application.havenskin.services.BlogHashtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/{id}")
    public BlogHashtag getBlogHashtagById(@PathVariable String id) {
        return blogHashtagService.getById(id);
    }

    // Lấy hashtag theo tên
    @GetMapping("/name")
    public BlogHashtag getBlogHashtagByName(@RequestParam String name) {
        return blogHashtagService.getByHashtagName(name);
    }

    // Thêm một hashtag mới
    @PostMapping
    public BlogHashtag addBlogHashtag(@RequestBody BlogHashtag blogHashtag) {
        return blogHashtagService.addBlogHashtag(blogHashtag);
    }

    // Cập nhật hashtag theo ID
    @PutMapping("/{id}")
    public BlogHashtag updateBlogHashtag(@PathVariable String id, @RequestBody BlogHashtag blogHashtag) {
        return blogHashtagService.updateBlogHashtag(id, blogHashtag);
    }

    // Xóa mềm hashtag theo ID
    @DeleteMapping("/{id}")
    public String deleteBlogHashtag(@PathVariable String id) {
        return blogHashtagService.deleteBlogHashtag(id);
    }
}
