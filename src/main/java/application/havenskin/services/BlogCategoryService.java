package application.havenskin.services;

import application.havenskin.models.BlogCategory;
import application.havenskin.models.Blogs;
import application.havenskin.repositories.BlogCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogCategoryService {
    @Autowired
    private BlogCategoryRepository blogCategoryRepository;

    public List<BlogCategory> getAll() {
        return blogCategoryRepository.findAll();
    }

    public BlogCategory getByBlogCategoryName(String blogCategoryName) {
        return blogCategoryRepository.findByBlogCategoryName(blogCategoryName);
    }

    public BlogCategory getByBlogCategoryId(String blogCategoryId) {
        return blogCategoryRepository.findById(blogCategoryId).get();
    }

    public BlogCategory addBlogCategory(BlogCategory blogCategory) {
        return blogCategoryRepository.save(blogCategory);
    }

    public BlogCategory updateBlogCategoryById(String id,BlogCategory blogCategory) {
        BlogCategory existingBlogCategory = blogCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));

        if(blogCategory.getBlogCategoryName() != null) {
            existingBlogCategory.setBlogCategoryName(blogCategory.getBlogCategoryName());
        }

        if(blogCategory.getDescription() != null) {
            existingBlogCategory.setDescription(blogCategory.getDescription());
        }

        if(blogCategory.getStatus() != 0){
            existingBlogCategory.setStatus(blogCategory.getStatus());
        }

        return blogCategoryRepository.save(existingBlogCategory);
    }

    public String deleteBlogCategoryById(String id) {
        BlogCategory blogCategory = blogCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
        blogCategory.setStatus((byte) 0); // Xóa mềm
        blogCategoryRepository.save(blogCategory);
        return "Blog deleted successfully";
    }
}
