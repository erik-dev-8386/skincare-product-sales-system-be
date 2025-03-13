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

//    public BlogCategory getByBlogCategoryId(String blogCategoryId) {
//        return blogCategoryRepository.findById(blogCategoryId).get();
//    }

    public BlogCategory addBlogCategory(BlogCategory blogCategory) {
        return blogCategoryRepository.save(blogCategory);
    }

//    public BlogCategory updateBlogCategoryById(String id,BlogCategory blogCategory) {
//        BlogCategory existingBlogCategory = blogCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
//
//        if(blogCategory.getBlogCategoryName() != null) {
//            existingBlogCategory.setBlogCategoryName(blogCategory.getBlogCategoryName());
//        }
//
//        if(blogCategory.getDescription() != null) {
//            existingBlogCategory.setDescription(blogCategory.getDescription());
//        }
//
//        if(blogCategory.getStatus() != 0){
//            existingBlogCategory.setStatus(blogCategory.getStatus());
//        }
//
//        return blogCategoryRepository.save(existingBlogCategory);
//    }

    public BlogCategory updateBlogCategoryByName(String blogCategoryName,BlogCategory blogCategory) {
        BlogCategory existingBlogCategory = blogCategoryRepository.findByBlogCategoryName(blogCategoryName)
                .orElseThrow(() -> new RuntimeException("No blog category found with name " + blogCategoryName));

        if(existingBlogCategory.getBlogCategoryName().isEmpty()) {
            throw  new RuntimeException("No blog category found with name " + blogCategoryName);
        }

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

//    public String deleteBlogCategoryById(String id) {
//        BlogCategory blogCategory = blogCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
//        blogCategory.setStatus((byte) 0); // Xóa mềm
//        blogCategoryRepository.save(blogCategory);
//        return "Blog deleted successfully";
//    }

    public String deleteBlogCategoryByName(String blogCategoryName) {
        BlogCategory existingBlogCategory = blogCategoryRepository.findByBlogCategoryName(blogCategoryName)
                .orElseThrow(() -> new RuntimeException("No blog category found with name " + blogCategoryName));

        if(existingBlogCategory.getBlogCategoryName().isEmpty()) {
            throw  new RuntimeException("No blog category found with name " + blogCategoryName);
        }
        existingBlogCategory.setStatus((byte) 0); // Xóa mềm
        blogCategoryRepository.save(existingBlogCategory);
        return "Blog deleted successfully";
    }
}
