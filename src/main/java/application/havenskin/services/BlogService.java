package application.havenskin.services;

import application.havenskin.models.Blogs;
import application.havenskin.repositories.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;
//    @Autowired
//    private Mapper mapper;
//    public List<Blogs>  getAllBlogs(){
//        return blogRepostiory.findAll();
//    }
//    public Blogs getBlogById(String id){
//        return blogRepostiory.findById(id).get();
//    }
//    public Blogs addBlog(Blogs blog){
//        return blogRepostiory.save(blog);
//    }
//    public Blogs updateBlog(String id, BlogDTO blog){
//        Blogs blogToUpdate = blogRepostiory.findById(id).orElseThrow(()-> new RuntimeException("Blog not found"));
//        mapper.updateBlogs(blogToUpdate, blog);
//        return blogRepostiory.save(blogToUpdate);
//    }
//    public void deleteBlog(String id){
//        blogRepostiory.deleteById(id);
//    }
//    public List<Blogs> addListOfBlogs(List<Blogs> blogs){
//        return blogRepostiory.saveAll(blogs);
//    }

    public List<Blogs> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Blogs getBlogById(String id) {
        return blogRepository.findById(id).get();
    }

    public Blogs getBlogByTitle(String title) {
        return blogRepository.findByBlogTitle(title);
    }

    public Blogs createBlog(Blogs blog) {
        return blogRepository.save(blog);
    }

    public Blogs updateBlogById(String id, Blogs blog) {
        Blogs existingBlog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        if (blog.getBlogCategory() != null) {
            existingBlog.setBlogCategory(blog.getBlogCategory());
        }
        if (blog.getBlogContent() != null) {
            existingBlog.setBlogContent(blog.getBlogContent());
        }
        if (blog.getBlogImages() != null) {
            existingBlog.setBlogImages(blog.getBlogImages());
        }
        if (blog.getHashtags() != null) {
            existingBlog.setHashtags(blog.getHashtags());
        }
        if (blog.getUserId() != null) {
            existingBlog.setUserId(blog.getUserId());
        }
        if (blog.getPostedTime() != null) {
            existingBlog.setPostedTime(blog.getPostedTime());
        }
        if (blog.getDeletedTime() != null) {
            existingBlog.setDeletedTime(blog.getDeletedTime());
        }
        if (blog.getStatus() != 0) {
            existingBlog.setStatus(blog.getStatus());
        }

        return blogRepository.save(existingBlog);
    }

    public String deleteBlogById(String id) {
        Blogs blog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
        blog.setStatus((byte) 0); // Xóa mềm
        blogRepository.save(blog);
        return "Blog deleted successfully";
    }

    public List<Blogs> getBlogByCategory(String categoryName) {
        return blogRepository.findByBlogCategory_BlogCategoryName(categoryName);
    }

    // Lấy blog theo hashtag
    public List<Blogs> getBlogsByHashtagName(String hashtagName) {
        return blogRepository.findByHashtagName(hashtagName);
    }
}

