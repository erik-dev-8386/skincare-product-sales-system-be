package application.havenskin.services;

import application.havenskin.models.BlogCategory;
import application.havenskin.repositories.BlogCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogCategoryService {
    @Autowired
    private BlogCategoryRepository blogCategoryRepository;

    public List<BlogCategory> getAll() {
        return blogCategoryRepository.findAll();
    }

    public Optional<BlogCategory> getByBlogCategoryName(String blogCategoryName) {
        return blogCategoryRepository.findByBlogCategoryName(blogCategoryName);
    }

    public BlogCategory addBlogCategory(BlogCategory blogCategory) {
        return blogCategoryRepository.save(blogCategory);
    }

    public BlogCategory updateBlogCategoryByName(String blogCategoryName, BlogCategory blogCategory) {
        BlogCategory existingBlogCategory = blogCategoryRepository.findByBlogCategoryName(blogCategoryName)
                .orElseThrow(() -> new RuntimeException("No blog category found with name " + blogCategoryName));

        if (blogCategory.getBlogCategoryName() != null) {
            existingBlogCategory.setBlogCategoryName(blogCategory.getBlogCategoryName());
        }
        if (blogCategory.getDescription() != null) {
            existingBlogCategory.setDescription(blogCategory.getDescription());
        }
        // Không kiểm tra status != 0, cho phép cập nhật mọi giá trị status
        existingBlogCategory.setStatus(blogCategory.getStatus());

        return blogCategoryRepository.save(existingBlogCategory);
    }

    public String deleteBlogCategoryByName(String blogCategoryName) {
        BlogCategory existingBlogCategory = blogCategoryRepository.findByBlogCategoryName(blogCategoryName)
                .orElseThrow(() -> new RuntimeException("No blog category found with name " + blogCategoryName));

        existingBlogCategory.setStatus((byte) 0); // Xóa mềm
        blogCategoryRepository.save(existingBlogCategory);
        return "Blog category deleted successfully";
    }
}