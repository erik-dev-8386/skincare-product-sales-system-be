package application.havenskin.services;

import application.havenskin.models.BlogHashtag;
import application.havenskin.repositories.BlogHashtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogHashtagService {
    @Autowired
    private BlogHashtagRepository blogHashtagRepository;

    public List<BlogHashtag> getAll() {
        return blogHashtagRepository.findAll();
    }

    public BlogHashtag getById(String id) {
        return blogHashtagRepository.findById(id).orElseThrow(() -> new RuntimeException("No hashtag found with id: " + id));
    }

    public Optional<BlogHashtag> getByHashtagName(String hashtagName) {
        return blogHashtagRepository.findByBlogHashtagName(hashtagName);
    }

    public BlogHashtag addBlogHashtag(BlogHashtag blogHashtag) {
        return blogHashtagRepository.save(blogHashtag);
    }

    public BlogHashtag updateBlogHashtagByHashtagName(String blogHashtagName, BlogHashtag blogHashtag) {
        Optional<BlogHashtag> optionalBlogHashtag = blogHashtagRepository.findByBlogHashtagName(blogHashtagName);
        BlogHashtag existingBlogHashtag = optionalBlogHashtag.orElseThrow(() -> new RuntimeException("No hashtag found with name: " + blogHashtagName));

        if (blogHashtag.getBlogHashtagName() != null) {
            existingBlogHashtag.setBlogHashtagName(blogHashtag.getBlogHashtagName());
        }
        if (blogHashtag.getDescription() != null) {
            existingBlogHashtag.setDescription(blogHashtag.getDescription());
        }
        // Xử lý status mà không kiểm tra != 0, để cho phép cập nhật về 0 nếu cần
        existingBlogHashtag.setStatus(blogHashtag.getStatus());

        return blogHashtagRepository.save(existingBlogHashtag);
    }

    public String deleteBlogHashtagByHashtagName(String blogHashtagName) {
        Optional<BlogHashtag> optionalBlogHashtag = blogHashtagRepository.findByBlogHashtagName(blogHashtagName);
        BlogHashtag existingBlogHashtag = optionalBlogHashtag.orElseThrow(() -> new RuntimeException("No hashtag found with name: " + blogHashtagName));
        existingBlogHashtag.setStatus((byte) 0);
        blogHashtagRepository.save(existingBlogHashtag);
        return "Blog hashtag has been deleted successfully";
    }
}